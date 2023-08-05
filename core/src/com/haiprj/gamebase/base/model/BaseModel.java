package com.haiprj.gamebase.base.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.haiprj.gamebase.base.screen.BaseScreen;
import com.haiprj.gamebase.interfaces.IModel;
import com.haiprj.gamebase.interfaces.OnClickListener;
import com.haiprj.gamebase.utils.GameUtils;
import com.haiprj.gamebase.utils.loader.G3DJLoader;

import java.util.HashMap;
import java.util.Objects;

public abstract class BaseModel extends ModelInstance implements IModel {

    protected BoundingBox bb;
    protected Vector3 realSize;
    protected final Vector3 position = new Vector3(0, 0, 0) ;
    protected final Vector3 beforePos = position;
    protected Vector3 direction;
    protected float currentRotateAngle = 0f;
    protected float scale = 0f;
    protected float speed = 5f;

    protected AnimationController animationController;
    protected OnClickListener clickListener;
    protected BaseScreen screen;
    protected final HashMap<String, Object> cacheData = new HashMap<>();


    public void setOnClickListener(BaseScreen screen, OnClickListener clickListener) {
        this.screen = screen;
        this.clickListener = clickListener;
    }

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
        this.position.set(position);
        this.transform.setTranslation(this.position);
        this.beforePos.set(this.position);
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
        currentRotateAngle += degree;
        if (currentRotateAngle <= 0 ) currentRotateAngle = 359;
        else if (currentRotateAngle >= 360) currentRotateAngle = 0;
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

    public void touchDown(int screenX, int screenY) {
        if (this.clickListener != null && this.screen != null) {
            Vector3 point = GameUtils.getWorldPositionOnClick(this.screen.getCamera(), this, screenX, screenY);
            if (point != null) {
                cacheData.remove("click_position");
                cacheData.put("click_position", point);
                this.clickListener.onClick(this, point);
            }
        }
    }

    public void touchDragged(int screenX, int screenY) {
        if (!canDrag()) return;
        if (this.clickListener != null && this.screen != null) {
            Vector3 point = GameUtils.getWorldPositionOnClick(this.screen.getCamera(), this, screenX, screenY);
            if (point != null) {
                this.setPosition(point);
                Vector3 v = null;
                if (cacheData.containsKey("click_position")) v = (Vector3) cacheData.get("click_position");
                this.clickListener.onDrag(this, v);
            }
        }
    }
    public void goFront(float delta) {
        float distance = speed * delta;
        float changeX = distance * MathUtils.sinDeg(currentRotateAngle);
        float changeZ = distance * MathUtils.cosDeg(currentRotateAngle);
        this.setPosition(this.position.x + changeX, this.position.y, this.position.z + changeZ);
    }

    public void goBack(float delta) {
        float distance = speed * delta;
        float changeX = distance * MathUtils.sinDeg(currentRotateAngle);
        float changeZ = distance * MathUtils.cosDeg(currentRotateAngle);
        this.setPosition(this.position.x - changeX, this.position.y, this.position.z - changeZ);
    }

    public void dispose() {
        cacheData.clear();
        this.model.dispose();
    }
}
