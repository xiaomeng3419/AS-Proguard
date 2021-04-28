package com.layton.zp.plugin.processor;

import com.layton.zp.plugin.config.LaytonConfiguration;
import com.layton.zp.plugin.processor.git.GitEngine;

import java.util.Hashtable;

/**
 * 模板方法
 */
public abstract class AbstractDecodeProcessor implements Processor {


    Engine engine;
    ProguardEngine proguardEngine;

    @Override
    public final String process() {
        if (engine.generateMapping()) {
            return proguardEngine.exe();
        } else {
            return "decode failed";
        }
    }
}
