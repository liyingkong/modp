package com.senlang.modp.api;

import com.mathworks.toolbox.javabuilder.MWException;
import com.senlang.modp.dao.UploadRepository;
import com.senlang.modp.dao.t_peoplefeatureRepo;
import com.senlang.modp.exception.StorageFileNotFoundException;
import com.senlang.modp.model.CommonResult;
import com.senlang.modp.model.CutVideoParam;
import com.senlang.modp.model.Upload;
import com.senlang.modp.model.t_peoplefeature;
import com.senlang.modp.service.JsonResultFactory;
import cutVideoOfCertain.CutVideo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trackOfMultityObject.Track;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private t_peoplefeatureRepo tpfr;
    @Autowired
    private UploadRepository ur;
    @Value("${file-path}")
    private String filepath;
    @Value("${cut-video-store-path}")
    private String cvsp;
    @Value("${track-video-path}")
    private String tvp;

    private final Logger logger = LogManager.getLogger();

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        return "hello";
    }

//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    @ResponseBody
//    public Object getPeoplesAll() {
//        PageRequest pageable = getPageRequest();
//        Page<t_peoplefeature> data = tpfr.findAll(pageable);
//        HashMap<String, Object> ret = JsonResultFactory.getOkResult();
//        ret.put("data", data);
//        return ret;
//    }
    @RequestMapping(value = "/nameList", method = RequestMethod.GET)
    @ResponseBody
    public String getPeoplesNameAll() {
        List<t_peoplefeature> tpfl = tpfr.findAll();
        List<String> peoples = tpfl.parallelStream().map(c -> "\""+c.name+"\"").collect(Collectors.toList());
        String peoplesNameStr = "["+String.join(",", peoples.toArray(new String[peoples .size()]))+"]";
        return peoplesNameStr;
    }

    @RequestMapping(value = "/cutVideo", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult cutvideo(@RequestBody CutVideoParam model) {
        try {
            CutVideo cutVideo = new CutVideo();
            String finalfilepath = filepath + "\\" + model.getUpload().getCode();
            System.out.println("finalfilepath");
            System.out.println(finalfilepath);
            String code = model.getUpload().getCode();
            Upload upload = ur.findByCode(code);
            upload.setCutVideoPath(code.substring(0,code.length()-4)+"_cut.mp4");
            upload = ur.save(upload);
            Object[] cutName = cutVideo.cutVideoOfCertain(1, model.getBox(), finalfilepath, cvsp);
            System.out.println(cutName[0]);
            return JsonResultFactory.getOkDataResult(upload);
        } catch (MWException e) {
            e.printStackTrace();
            return JsonResultFactory.getCommonResult(-1);
        } finally {
        }
    }

    @GetMapping("/getCutVideo/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getcutvideo(@PathVariable String filename) {
        System.out.println("getCutVideo");
        System.out.println(filename);
        Upload upload = ur.findByCutVideoPath(filename);
        try {
            Path file = Paths.get(cvsp + "\\" + filename);
            System.out.println(cvsp + "\\" + filename);
            System.out.println(file);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + upload.getName() + "\"")
                        .body(resource);
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }

    }

    @RequestMapping(value = "/getTrackVideo", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getTrack(@RequestParam String filename) {
        try {
            Upload upload = ur.findByCutVideoPath(filename);
            Track track = new Track();
            String cutName = cvsp + "\\" +filename;
            String storeAddress = tvp;
            Object[] output = track.trackOfMultityObject(2, cutName, storeAddress);
            upload.setVideoTrackPath(output[0].toString());
            upload.setVideoMaskPath(output[1].toString());
            return JsonResultFactory.getOkDataResult(ur.save(upload));
        }catch(MWException e) {
            e.printStackTrace();
            return JsonResultFactory.getCommonResult(-1);
        }finally {
        }
    }

    @GetMapping("/getTrackVideo/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getTrackVideo(@PathVariable String filename) {
//        System.out.println("getCutVideo");
//        System.out.println(filename);
        Upload upload = ur.findByCutVideoPath(filename);
        try {
            Path file = Paths.get(upload.getVideoTrackPath());
            System.out.println(upload.getVideoTrackPath());
            System.out.println(file);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + upload.getName() + "\"")
                        .body(resource);
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }

    }

    @GetMapping("/getMaskVideo/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getMaskVideo(@PathVariable String filename) {
//        System.out.println("getCutVideo");
//        System.out.println(filename);
        Upload upload = ur.findByCutVideoPath(filename);
        try {
            Path file = Paths.get(upload.getVideoMaskPath());
            System.out.println(upload.getVideoTrackPath());
            System.out.println(file);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + upload.getName() + "\"")
                        .body(resource);
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }

    }
}
