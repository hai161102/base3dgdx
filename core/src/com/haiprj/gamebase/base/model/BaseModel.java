package com.haiprj.gamebase.base.model;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.haiprj.gamebase.utils.GameUtils;
import com.haiprj.gamebase.utils.loader.G3DJLoader;

public abstract class BaseModel extends ModelInstance {

    protected BoundingBox bb;
    protected Vector3 realSize;
    protected Vector3 position;
    protected Vector3 direction;
    protected float scale = 0f;
    protected AnimationController animationController;
    public int loopCount = 1;
    public BaseModel(Model model) {
        super(model);
        this.init();
    }
    public BaseModel(String path) {
        this(G3DJLoader.getInstance().loadModel(path));
    }
    public BaseModel(ModelInstance copyFrom) {
        super(copyFrom);
        this.init();
    }
    protected void init() {
        this.scale = GameUtils.CONFIG_SIZE / this.findMinSize();
        scale(scale);
        this.animationController = new AnimationController(this);
        this.setPosition(this.transform.getTranslation(new Vector3()));
    }

    protected float findMinSize() {
        bb = getBoundingBox();
        Vector3 v3 = getRealSize();
        float[] l = {v3.x, v3.y, v3.z};
        float min = l[0];
        for (float v : l) {
            if (min > v) min = v;
        }
        return min;
    }

    protected BoundingBox getBoundingBox() {
        if (bb == null) {
            bb = this.calculateBoundingBox(new BoundingBox());
        }
        return bb;
    }

    protected Vector3 getRealSize() {
        if (realSize == null) realSize = getBoundingBox().getDimensions(new Vector3());
        return realSize;
    }
    protected Vector3 getPercentSize() {
        float min = this.findMinSize();
        return new Vector3(realSize.x / min, realSize.y / min, realSize.z / min);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getPositionForCam() {
        Vector3 p = position;
        BoundingBox b = new BoundingBox();
        this.calculateBoundingBox(b);
        p.y -= b.getHeight();
        return p;
    }
    public void setPosition(Vector3 position) {
        this.position = position;
        this.transform.setTranslation(this.position);
    }

    public void setPosition(float x, float y, float z) {
        setPosition(new Vector3(x, y, z));
    }

    public Vector3 getSize() {
        return realSize;
    }

    public void setSize(Vector3 size) {
        this.realSize = size;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public void setDirection(Vector3 direction) {
        this.direction = direction;
    }

    public void rotate(Vector3 rotateAxis, float degree) {
        this.transform.rotate(rotateAxis, degree);
    }

    public void scale(float num) {
        this.scale(num, num, num);
    }

    public void scale(float x, float y, float z) {
        this.transform.scale(x, y, z);
    }

    public void scale(Vector3 scale) {
        this.scale(scale.x, scale.y, scale.z);
    }
    public abstract void update(float dt);
    public void setAnimation(Animation source, AnimationController.AnimationListener animationListener) {
        this.animationController.setAnimation(source.id, loopCount, animationListener);
    }
    public void update(float dt, ModelBatch modelBatch, Environment environment) {
        modelBatch.render(this, environment);
        if (this.animationController != null) {
            this.animationController.update(dt);
        }
    }

    public void dispose() {
        this.model.dispose();
    }
}
