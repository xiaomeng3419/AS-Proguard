package com.layton.zp.plugin.processor;

import com.layton.zp.plugin.processor.git.GitEngine;

import java.util.Hashtable;

public class ProguardDecodeProcessor extends AbstractDecodeProcessor{
    public ProguardDecodeProcessor(Engine engine,ProguardEngine proguardEngine) {
        this.engine = engine;
        this.proguardEngine = proguardEngine;
    }
}
