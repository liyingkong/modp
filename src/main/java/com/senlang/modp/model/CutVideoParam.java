package com.senlang.modp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
public class CutVideoParam {
//    id: number;
//    box:number[];
//    upload:Upload;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Upload upload;

    private Double[] box;

}
