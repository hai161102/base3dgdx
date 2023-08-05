package com.haiprj.gamebase.utils.views;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class View extends Texture {

    public final Rectangle rect = new Rectangle(0, 0, 0, 0);
    public float padding = 0f;
    public float margin = 0f;

    public View(String internalPath) {
        super(internalPath);
    }

    public View(FileHandle file) {
        super(file);
    }

    public View(FileHandle file, boolean useMipMaps) {
        super(file, useMipMaps);
    }

    public View(FileHandle file, Pixmap.Format format, boolean useMipMaps) {
        super(file, format, useMipMaps);
    }

    public View(Pixmap pixmap) {
        super(pixmap);
    }

    public View(Pixmap pixmap, boolean useMipMaps) {
        super(pixmap, useMipMaps);
    }

    public View(Pixmap pixmap, Pixmap.Format format, boolean useMipMaps) {
        super(pixmap, format, useMipMaps);
    }

    public View(int width, int height, Pixmap.Format format) {
        super(width, height, format);
    }

    public View(TextureData data) {
        super(data);
    }

    protected View(int glTarget, int glHandle, TextureData data) {
        super(glTarget, glHandle, data);
    }

    public void drawUI(SpriteBatch batch, float delta) {
        batch.draw(this, rect.x, rect.y, rect.width, rect.height);
    }
}
