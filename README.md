# 百变圆柱积木
使用方法：
1.本地运行项目
2.使用postman通过post方式调用127.0.0.1:8088/testUtil/cylinderPuzzle
3.入参格式如下：
```
  {
    "order": 2, // 阶数，即圆柱的高度
    "puzzles": [ // 积木的列表
        {
            "isCentral": false, // 是否为带圆环的中心积木
            "sequenceNum": 1,   // 当前积木顺序
            "color": "red",     // 当前积木颜色(可不填) 
            "coordinates": [    // 当前积木的方块列表，使用方块右上角坐标代表此方块
                {
                    "x": 1,
                    "y": 1
                },
                {
                    "x": 1,
                    "y": 2
                },
                {
                    "x": 2,
                    "y": 1
                },
                {
                    "x": 3,
                    "y": 1
                },
                {
                    "x": 4,
                    "y": 1
                },
                {
                    "x": 5,
                    "y": 1
                },
                {
                    "x": 6,
                    "y": 1
                },
                {
                    "x": 7,
                    "y": 1
                },
                {
                    "x": 8,
                    "y": 1
                },
                {
                    "x": 9,
                    "y": 1
                },
                {
                    "x": 10,
                    "y": 1
                }
            ]
        },
        {
            "isCentral": true,
            "sequenceNum": 2,
            "color": "blue",
            "coordinates": [
                {
                    "x": 1,
                    "y": 1
                }
            ]
        },
        {
            "isCentral": true,
            "sequenceNum": 3,
            "color": "yellow",
            "coordinates": [
                {
                    "x": 1,
                    "y": 1
                }
            ]
        },
        {
            "isCentral": false,
            "sequenceNum": 4,
            "color": "white",
            "coordinates": [
                {
                    "x": 1,
                    "y": 1
                },
                {
                    "x": 1,
                    "y": 2
                },
                {
                    "x": 2,
                    "y": 1
                },
                {
                    "x": 3,
                    "y": 1
                },
                {
                    "x": 4,
                    "y": 1
                },
                {
                    "x": 5,
                    "y": 1
                },
                {
                    "x": 6,
                    "y": 1
                },
                {
                    "x": 7,
                    "y": 1
                },
                {
                    "x": 8,
                    "y": 1
                },
                {
                    "x": 9,
                    "y": 1
                },
                {
                    "x": 10,
                    "y": 1
                }
            ]
        }
    ]
}
```

4.出参格式如下：
```
{
    "code": "200",
    "type": "success",
    "message": "操作成功!",
    "data": {
        "hasSolution": true, // 是否有解
        "solution": [
            {
                "isCentral": false,
                "sequenceNum": 4,
                "color": "white",
                "coordinates": [  // 此积木在圆柱中的最终位置
                    {
                        "x": 12,
                        "y": 2
                    },
                    {
                        "x": 12,
                        "y": 1
                    },
                    {
                        "x": 11,
                        "y": 2
                    },
                    {
                        "x": 10,
                        "y": 2
                    },
                    {
                        "x": 9,
                        "y": 2
                    },
                    {
                        "x": 8,
                        "y": 2
                    },
                    {
                        "x": 7,
                        "y": 2
                    },
                    {
                        "x": 6,
                        "y": 2
                    },
                    {
                        "x": 5,
                        "y": 2
                    },
                    {
                        "x": 4,
                        "y": 2
                    },
                    {
                        "x": 3,
                        "y": 2
                    }
                ]
            },
            {
                "isCentral": true,
                "sequenceNum": 3,
                "color": "yellow",
                "coordinates": [
                    {
                        "x": 11,
                        "y": 1
                    }
                ]
            },
            {
                "isCentral": true,
                "sequenceNum": 2,
                "color": "blue",
                "coordinates": [
                    {
                        "x": 2,
                        "y": 2
                    }
                ]
            },
            {
                "isCentral": false,
                "sequenceNum": 1,
                "color": "red",
                "coordinates": [
                    {
                        "x": 1,
                        "y": 1
                    },
                    {
                        "x": 1,
                        "y": 2
                    },
                    {
                        "x": 2,
                        "y": 1
                    },
                    {
                        "x": 3,
                        "y": 1
                    },
                    {
                        "x": 4,
                        "y": 1
                    },
                    {
                        "x": 5,
                        "y": 1
                    },
                    {
                        "x": 6,
                        "y": 1
                    },
                    {
                        "x": 7,
                        "y": 1
                    },
                    {
                        "x": 8,
                        "y": 1
                    },
                    {
                        "x": 9,
                        "y": 1
                    },
                    {
                        "x": 10,
                        "y": 1
                    }
                ]
            }
        ]
    }
}
```
