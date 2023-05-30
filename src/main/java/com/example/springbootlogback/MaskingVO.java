package com.example.springbootlogback;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用途: TODO
 *
 * @author: ADAM
 * @create: 2023/05/30 09:20
 * @version: 1.0
 */
@Data
@AllArgsConstructor
public class MaskingVO {
    private int id;
    private String name;
    private String personName;
}
