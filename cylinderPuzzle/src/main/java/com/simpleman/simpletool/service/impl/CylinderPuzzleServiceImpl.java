package com.simpleman.simpletool.service.impl;


import com.simpleman.simpletool.pojo.Coordinate;
import com.simpleman.simpletool.pojo.Puzzle;
import com.simpleman.simpletool.pojo.PuzzleBase;
import com.simpleman.simpletool.service.CylinderPuzzleService;
import com.simpleman.simpletool.vo.req.CylinderPuzzleReq;
import com.simpleman.simpletool.vo.resp.CylinderPuzzleResp;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 描述
 *
 * @author l30035293
 * @since 2024-05-08
 */
@Service
public class CylinderPuzzleServiceImpl implements CylinderPuzzleService {

    private int[][] cylinderMap;

    private int cylinderOrder;

    private static final int CYLINDER_HORIZON_GRID_NUM = 12;

    @Override
    public CylinderPuzzleResp solveCylinderPuzzle(CylinderPuzzleReq cylinderPuzzleReq) {
        /*
        思路：拿一块积木放好，再紧挨着它放下一块，直到所有积木用完、所有位置被填满，并且没有移除
        放积木时穷举所有可放位置(紧挨着上一块,枚举出上一块) + 穷举所有角度(正、反)
         */
        cylinderOrder = cylinderPuzzleReq.getOrder();
        // 坐标->是否被填充
        cylinderMap = new int[CYLINDER_HORIZON_GRID_NUM + 1][cylinderOrder + 1];
        for (int x = 0; x < CYLINDER_HORIZON_GRID_NUM; x++) {
            for (int y = 0; y < cylinderPuzzleReq.getOrder(); y++) {
                cylinderMap[x][y] = 0;
            }
        }
        initPuzzles(cylinderPuzzleReq.getPuzzles());

        CylinderPuzzleResp resp = new CylinderPuzzleResp();
        ArrayList<Puzzle> puzzles = enumeratePuzzle(cylinderPuzzleReq.getPuzzles(), 1, null, null);
        ArrayList<PuzzleBase> solution = new ArrayList<>();
        if (puzzles != null) {
            resp.setHasSolution(true);
            for (Puzzle curPuzzle : puzzles) {
                solution.add(new PuzzleBase(curPuzzle));
            }
        } else {
            solution = null;
        }
        resp.setSolution(solution);
        return resp;
    }

    /**
     * 初始化所有积木，计算可用形状的坐标
     *
     * @param puzzles 积木集合
     */
    private void initPuzzles(ArrayList<Puzzle> puzzles) {
        // 顺时针拧90° 拧三次+原本形状 一种积木得到四种可能
        // 排除：旋转后高度超过order
        for (Puzzle curPuzzle : puzzles) {
            curPuzzle.setAvailableForms(new ArrayList<>());
            ArrayList<Coordinate> curPuzzleForm = curPuzzle.getCoordinates();
            if (curPuzzle.getIsCentral()) { // 中心积木无需旋转
                curPuzzle.getAvailableForms().add(curPuzzleForm);
                continue;
            }
            // 旋转当前积木三次，保存可用形态，第一次循环判断可用性
            for (int i = 0; i < 4; i++) {
                if (i != 0) { // 第一次循环仅判断可用性，不旋转
                    curPuzzleForm = rotatePuzzle(curPuzzleForm);
                }
                if (isAvailableForm(curPuzzleForm) && !isDuplicateForm(curPuzzle.getAvailableForms(), curPuzzleForm)) {
                    curPuzzle.getAvailableForms().add(curPuzzleForm);
                }
            }
        }
    }

    /**
     * 将积木顺时针旋转90°
     *
     * @param puzzleForm 积木的坐标列表
     * @return 积木顺时针旋转90°后的坐标列表
     */
    private ArrayList<Coordinate> rotatePuzzle(ArrayList<Coordinate> puzzleForm) {
        /*
        坐标规则：以方格右上角的坐标代表此方格
        1. 找到中心点
        2. 所有坐标依据中心点为0,0调整坐标 找到中心点 减去中心点x y
        3. 顺时针旋转规则 -x -> y  y -> x
        4. 顺时针旋转后原本的右上角变成了右下角
        5. 移到第一象限 右下角的点 x>=1 y>=0 则统计右下角模式的minx miny
              若 minx<1 则所有x + 1 + -minx  若miny<0 则所有的y + -miny
        6. 移动到第一象限后 根据所有右下角 y+1 计算出 右上角
        */
        /// 1. 找到中心点
        // 遍历所有坐标，分别找到x、y的最大值、最小值，计算中心点
        int minX, maxX, minY, maxY;
        minX = minY = Integer.MAX_VALUE;
        maxX = maxY = Integer.MIN_VALUE;
        for (Coordinate curCoordinate : puzzleForm) {
            minX = Math.min(curCoordinate.getX(), minX);
            minY = Math.min(curCoordinate.getY(), minY);
            maxX = Math.max(curCoordinate.getX(), maxX);
            maxY = Math.max(curCoordinate.getY(), maxY);
        }
        Coordinate centerPoz = new Coordinate((minX + maxX) / 2, (minY + maxY) / 2);

        /// 2. 所有坐标减去中心点
        /// 3. 顺时针旋转 -x -> y  y -> x
        ArrayList<Coordinate> rotatedForm = new ArrayList<>();
        minX = minY = Integer.MAX_VALUE; // 旋转后的x、y坐标最小值，用于积木移到第一象限
        for (Coordinate curCoordinate : puzzleForm) {
            Coordinate curRotatedCoordinate = new Coordinate(curCoordinate.getY() - centerPoz.getY(), centerPoz.getX() - curCoordinate.getX());
            rotatedForm.add(curRotatedCoordinate);
            minX = Math.min(curRotatedCoordinate.getX(), minX);
            minY = Math.min(curRotatedCoordinate.getY(), minY);
        }

        /// 4. 5. 6. 旋转后右上角变成了右下角，积木移动到第一象限，y+1 计算出 右上角
        for (Coordinate curCoordinate : rotatedForm) {
            if (minX < 1) {
                curCoordinate.setX(curCoordinate.getX() + 1 - minX);
            }
            if (minY < 0) {
                curCoordinate.setY(curCoordinate.getY() - minY);
            }
            curCoordinate.setY(curCoordinate.getY() + 1); // y+1 计算出 右上角
        }

        return rotatedForm;
    }

    /**
     * 判断积木形态是否可用
     *
     * @param puzzleForm 积木的坐标列表
     * @return 当前积木形态是否能够放入柱状体中
     */
    private boolean isAvailableForm(ArrayList<Coordinate> puzzleForm) {
        // 若此积木形态高于圆柱体阶数，则不可用
        for (Coordinate curPuzzleForm : puzzleForm) {
            if (curPuzzleForm.getY() > cylinderOrder || curPuzzleForm.getX() >= CYLINDER_HORIZON_GRID_NUM) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断当前积木是否与可用列表中的某块积木形状重合了
     *
     * @param availableForms 可用积木形态列表
     * @param curPuzzleForm 当前积木形态
     * @return 是否重合
     */
    private boolean isDuplicateForm(ArrayList<ArrayList<Coordinate>> availableForms, ArrayList<Coordinate> curPuzzleForm) {
        /*
        1. 使用map将curPuzzleForm所有坐标储存起来
        2. 遍历所有availableForms
        3. 遍历当前availableForm的所有坐标
        4. 若重复个数 = curPuzzleForm.size() 则完全重复
         */
        Set<Coordinate> curPuzzleMap = new HashSet<>(curPuzzleForm);
        for (ArrayList<Coordinate> availableForm : availableForms) {
            int duplicateNum = 0;
            for (Coordinate curCoordinate : availableForm) {
                if (curPuzzleMap.contains(curCoordinate)) {
                    duplicateNum++;
                } else {
                    break;
                }
            }
            if (duplicateNum == curPuzzleForm.size()) { // 形状完全重合
                return true;
            }
        }
        return false;
    }

    /**
     * 枚举所有积木的摆放情况，若所有积木都能合理摆放，并且将map完全填充，返回true
     *
     * @param puzzles 积木列表
     * @param usedPuzzleNum 当前使用的是第几块积木
     * @param curAvailablePoz 当前可用摆放的起点
     * @param centerPlaced 记录每一行中心积木是否摆放
     * @return 所有积木是否都能合理摆放，并且将map完全填充
     */
    private ArrayList<Puzzle> enumeratePuzzle(ArrayList<Puzzle> puzzles, int usedPuzzleNum, Set<Coordinate> curAvailablePoz, boolean[] centerPlaced) {
        if (usedPuzzleNum == 1) {
            curAvailablePoz = new HashSet<>();
            curAvailablePoz.add(new Coordinate(1, 1));
            centerPlaced = new boolean[cylinderOrder + 1];
        }
        for (Puzzle curPuzzle : puzzles) {
            if (curPuzzle.isUsed()) {
                continue;
            }
            for (ArrayList<Coordinate> curForm : curPuzzle.getAvailableForms()) { // 遍历当前所有可用形态
                for (Coordinate availablePoz : curAvailablePoz) { // 遍历当前所有可用起点
                    if (curPuzzle.getIsCentral() && centerPlaced[availablePoz.getY()]) { // 一行只能放一个中心积木
                        continue;
                    }
                    for (Coordinate curStartPoint : curForm) {
                        // 当前可用形态中的所有坐标都可作为起点，也会产生不同的后续
                        ArrayList<Coordinate> placedFormCoordinate = placePuzzle(availablePoz, curForm, curStartPoint); // 将当前积木放到map中
                        if (placedFormCoordinate != null) { // 若能正确放置
                            if (curPuzzle.getIsCentral()) {
                                centerPlaced[availablePoz.getY()] = true;
                            }
                            curPuzzle.setUsed(true); // 能正确放置，则将此积木标记已用
                            ArrayList<Coordinate> originalCoordinate = curPuzzle.getCoordinates(); // 缓存当前积木的原坐标
                            curPuzzle.setCoordinates(placedFormCoordinate);  // 将当前积木的坐标替换成放置后的坐标
                            if (usedPuzzleNum == puzzles.size() && isMapFullFilled()) { // 最后一块，且能正确放入，且图已被填满
                                return new ArrayList<>(Collections.singletonList(curPuzzle));
                            } else { // 未到最后一块积木，则继续枚举
                                Set<Coordinate> newAvailablePoz = updateAvailablePoz(placedFormCoordinate, curAvailablePoz);
                                ArrayList<Puzzle> ans = enumeratePuzzle(puzzles, usedPuzzleNum + 1, newAvailablePoz, centerPlaced);
                                if (ans != null) {
                                    ans.add(curPuzzle);
                                    return ans;
                                } else {
                                    curPuzzle.setUsed(false);  // 后续无解，取出此积木并标记为未使用
                                    curPuzzle.setCoordinates(originalCoordinate); // 后续无解，则当前积木恢复成原坐标
                                    rollbackMap(placedFormCoordinate); // 后续无解，则将本次放置的积木拿出来
                                    if (curPuzzle.getIsCentral()) {
                                        centerPlaced[availablePoz.getY()] = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 将已填入的积木拿出
     *
     * @param placedFormCoordinate
     */
    private void rollbackMap(ArrayList<Coordinate> placedFormCoordinate) {
        for (Coordinate placedCoordinate : placedFormCoordinate) {
            cylinderMap[placedCoordinate.getX()][placedCoordinate.getY()] = 0;
        }
    }

    /**
     * 新积木放入后，可用位置列表更新
     *
     * @param placedFormCoordinate 放入后积木在图中的坐标
     * @param curAvailablePoz 当前可用位置
     * @return 新的可用位置列表
     */
    private Set<Coordinate> updateAvailablePoz(ArrayList<Coordinate> placedFormCoordinate, Set<Coordinate> curAvailablePoz) {
        Set<Coordinate> newAvailablePoz = new HashSet<>(curAvailablePoz);
        int[][] dir = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}};
        for (Coordinate placedCoordinate : placedFormCoordinate) {
            newAvailablePoz.remove(placedCoordinate);
            // 遍历新放入积木每个方块的八个方向，将空余位置加入可用起点列表
            for (int i = 0; i < 8; i++) {
                int newX = placedCoordinate.getX() + dir[i][0];
                newX = (newX % 13 + newX / 13) % 13;
                if (newX <= 0) {
                    newX = 12 + newX;
                }
                int newY = placedCoordinate.getY() + dir[i][1];
                if (newY < 1 || newY > cylinderOrder || cylinderMap[newX][newY] == 1) {
                    // 纵坐标越界、或者当前位置已被占用，则不可作为起点
                    continue;
                }
                newAvailablePoz.add(new Coordinate(newX, newY));
            }
        }
        return newAvailablePoz;
    }

    /**
     * 在当前可用位置放入当前积木形状
     *
     * @param availablePoz 可用位置 方块的右上角
     * @param curForm 填入图的积木形状坐标
     * @param curStartPoint 当前可用形态中的起点，尝试放入到availablePoz，其他坐标找到相对此起点的位置填入
     * @return 放入后积木在图中的坐标 若无法放入则返回null
     */
    private ArrayList<Coordinate> placePuzzle(Coordinate availablePoz, ArrayList<Coordinate> curForm, Coordinate curStartPoint) {
        ArrayList<Coordinate> placedFormCoordinate = new ArrayList<>();
        int[][] cacheMap = Arrays.stream(cylinderMap).map(int[]::clone).toArray(int[][]::new);
        int offsetX = availablePoz.getX() - curStartPoint.getX();
        int offsetY = availablePoz.getY() - curStartPoint.getY();
        for (Coordinate curCoordinate : curForm) {
            int placedX = curCoordinate.getX() + offsetX;
            placedX = (placedX % 13 + placedX / 13) % 13; // 由于map是圆柱侧面展开，则横坐标首尾相连，横坐标范围[1,12]
            if (placedX <= 0) {
                placedX = 12 + placedX;
            }
            int placedY = curCoordinate.getY() + offsetY;
            if (placedY < 1 || placedY > cylinderOrder || cacheMap[placedX][placedY] == 1) {
                // 若当前积木放不进map中，返回null
                return null;
            }
            // 当前方块能放入map，更新map
            cacheMap[placedX][placedY] = 1;
            placedFormCoordinate.add(new Coordinate(placedX, placedY));
        }
        // 积木的所有方块均已放入，更新map
        cylinderMap = cacheMap;
        return placedFormCoordinate;
    }

    /**
     * 根据坐标填充情况映射表判断圆柱体是否被完全填充
     *
     * @return 是否被完全填充
     */
    private boolean isMapFullFilled() {
        for (int x = 1; x < cylinderOrder + 1; x++) {
            for (int y = 1; y < cylinderOrder + 1; y++) {
                if (cylinderMap[x][y] == 0) {
                    // 若存在未被填充的方块，返回false
                    return false;
                }
            }
        }
        return true;
    }
}
