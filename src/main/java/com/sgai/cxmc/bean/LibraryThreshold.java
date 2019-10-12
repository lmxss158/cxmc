package com.sgai.cxmc.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/8/8 16:09
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibraryThreshold {
    String libraryName;
    Double max;
    Double min;
    Double warn;
    String unit;

}
