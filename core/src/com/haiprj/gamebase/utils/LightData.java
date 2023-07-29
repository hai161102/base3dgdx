package com.haiprj.gamebase.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LightData extends DirectionalShadowLight {

    protected Environment environment;
    protected ModelBatch shadowBatch;
    protected Camera camera;
    public Vector3 vector;
    public LightData(int shadowMapWidth, int shadowMapHeight, float shadowViewportWidth, float shadowViewportHeight, float shadowNear, float shadowFar) {
        super(shadowMapWidth, shadowMapHeight, shadowViewportWidth, shadowViewportHeight, shadowNear, shadowFar);
        this.set(0.8f, 0.8f, 0.8f, -10f, -10f, -10f);

    }

    public void setData(Environment environment, Camera camera) {
        this.environment = environment;
        this.camera = camera;
        load();
    }

    private void load() {
        this.environment.add(this);
        this.environment.shadowMap = this;
        shadowBatch = new ModelBatch(new DepthShaderProvider());
    }

    public void update(float dt, ModelInstance... data) {
        if (this.environment != null && this.camera != null) {
            if (vector == null) {
                this.begin(Vector3.Zero, camera.direction);
            }
            else this.begin(vector, camera.direction);
            shadowBatch.begin(this.getCamera());
            for (ModelInstance d : data) {

                shadowBatch.render(d);
            }
            shadowBatch.end();
            this.end();
        }
    }
    public void updateLight(float dt, Object... data) {
        final List<ModelInstance> listModel = new ArrayList<>();
        for (Object datum : data) {
            if (datum instanceof ModelInstance || datum instanceof List){
                if (datum instanceof List) {
                    //noinspection unchecked
                    listModel.addAll((Collection<? extends ModelInstance>) datum);
                }
                else listModel.add((ModelInstance) datum);
            }

        }
        ModelInstance[] d = new ModelInstance[listModel.size()];
        for (ModelInstance modelInstance : listModel) {
            d[listModel.indexOf(modelInstance)] = modelInstance;

        }
        this.update(dt, d);
    }
}
