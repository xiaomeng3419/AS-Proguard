package com.layton.zp.plugin.config;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 *  持久化存储设置
 */
@State(name = "LaytonPersistent",storages = {@Storage(value = "layton_persistent.xml")})
public class LaytonPropertiesComponent implements PersistentStateComponent<LaytonPropertiesComponent.LaytonState> {
    private static LaytonPropertiesComponent  laytonPropertiesComponent = new LaytonPropertiesComponent();

    private  LaytonPropertiesComponent(){}
    public static LaytonPropertiesComponent getInstance(){
        if (laytonPropertiesComponent == null){
            return new LaytonPropertiesComponent();
        }else {
            return laytonPropertiesComponent;
        }
    }

    LaytonState laytonState = new LaytonState();

    @Nullable
    @Override
    public LaytonState getState() {
        return laytonState;
    }

    @Override
    public void loadState(@NotNull LaytonState laytonState) {
        XmlSerializerUtil.copyBean(laytonState,this.laytonState);
//        this.laytonState = laytonState;
    }

    public void setState(String state){
//        this.laytonState.branches.add(state);
        this.laytonState.branch = state;
    }

    static class LaytonState{
        String branch;

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }
        /*   List<String> branches  = new ArrayList<>();

        public List<String> getBranches() {
            return branches;
        }

        public void setBranches(List<String> branches) {
            this.branches = branches;
        }*/

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LaytonState that = (LaytonState) o;

            return branch != null ? branch.equals(that.branch) : that.branch == null;
        }

        @Override
        public int hashCode() {
            return branch != null ? branch.hashCode() : 0;
        }
    }
}
