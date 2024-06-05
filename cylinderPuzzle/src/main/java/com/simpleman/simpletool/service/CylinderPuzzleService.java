package com.simpleman.simpletool.service;


import com.simpleman.simpletool.vo.req.CylinderPuzzleReq;
import com.simpleman.simpletool.vo.resp.CylinderPuzzleResp;

/**
 * 描述
 *
 * @author l30035293
 * @since 2024-05-08
 */
public interface CylinderPuzzleService {
    CylinderPuzzleResp solveCylinderPuzzle(CylinderPuzzleReq cylinderPuzzleReq);
}