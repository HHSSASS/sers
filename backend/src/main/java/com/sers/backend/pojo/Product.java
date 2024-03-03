package com.sers.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String photo;
    private String name;
    private String description;
    private Integer price;
}
