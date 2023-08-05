package com.haiprj.gamebase.utils.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.haiprj.gamebase.enums.Orientation;

import java.util.ArrayList;
import java.util.List;

public class ScrollView<T extends View> implements InputProcessor {
    private Orientation orientation;
    private final Vector2 currentPos = new Vector2(0, 0);
    private final Vector2 beforePos;
    public final Rectangle rect = new Rectangle(0, 0, 100, 100);
    public final Rectangle contentSize = new Rectangle(0, 0, 0, 0);
    public float padding = 0f;
    public boolean hideDefaultRect = false;
    private ShapeRenderer shapeRenderer;
    private boolean isLeft = false, isRight = false, isUp = false, isDown = false;
    private final SpriteBatch batch;

    private final List<T> contents = new ArrayList<>();

    public ScrollView(SpriteBatch batch) {
        this.batch = batch;
        beforePos = new Vector2(currentPos);
        init();
        Gdx.input.setInputProcessor(this);
    }

    protected void init() {
        orientation =Orientation.VERTICAL;
        this.shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(Color.GREEN);
    }

    public void add(T data) {

    }

    public void refreshView() {
        if (this.contents.size() == 0) return;
        if (orientation == Orientation.VERTICAL){
            this.contentSize.x = this.rect.x + padding;
            this.contentSize.width = this.rect.width - padding * 2;
            for (int i = 0; i < contents.size(); i++) {
                T content = contents.get(i);
                content.rect.x = this.contentSize.x + content.margin;
                if (i == 0 ) {
                    content.rect.y = this.contentSize.y + content.margin + padding;
                }
                else {
                    content.rect.y = contents.get(i - 1).rect.y + contents.get(i - 1).margin + content.margin;
                }
                content.rect.width = contentSize.width;
                content.rect.height = contentSize.height;
            }
        }
        else if (orientation == Orientation.HORIZONTAL) {
            this.contentSize.y = this.rect.y + padding;
            this.contentSize.height = this.rect.height - padding * 2;
            for (int i = 0; i < contents.size(); i++) {
                T content = contents.get(i);
                content.rect.y = this.contentSize.y + content.margin;
                if (i == 0 ) {
                    content.rect.x = this.contentSize.x + content.margin + padding;
                }
                else {
                    content.rect.x = contents.get(i - 1).rect.x + contents.get(i - 1).margin + content.margin;
                }
                content.rect.width = contentSize.width;
                content.rect.height = contentSize.height;
            }
        }

    }

    public void update(float delta) {

        if (!hideDefaultRect) drawDefaultBound(delta);

        if (isUp) {
            this.scrollUP(delta);
        }
        else  if (isDown) {
            this.scrollDown(delta);
        }
        if (isLeft) {
            this.scrollLeft(delta);
        }
        else if (isRight) this.scrollRight(delta);

        batch.begin();
        for (T content : this.contents) {
            content.drawUI(this.batch, delta);
        }
        batch.end();
    }

    private void scrollRight(float delta) {
        System.out.println("scrollRight");
    }

    private void scrollLeft(float delta) {
        System.out.println("scrollLeft");
    }

    private void scrollDown(float delta) {
        System.out.println("scrollDown");
    }

    private void scrollUP(float delta) {
        System.out.println("scrollUP");
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isUp = isDown = isLeft = isRight = false;
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        isUp = isDown = isLeft = isRight = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!rect.contains(screenX, screenY)) return false;
        currentPos.set(screenX, screenY);

        if (orientation == Orientation.VERTICAL) {
            if (beforePos.y < currentPos.y) {
                isDown = true;
                isUp = false;
            }
            else if (beforePos.y > currentPos.y) {
                isUp = true;
                isDown = false;
            }

        }
        if (orientation == Orientation.HORIZONTAL) {
            if (beforePos.x < currentPos.x) {
                isLeft = true;
                isRight = false;
            }
            else if (beforePos.x > currentPos.y) {
                isRight = true;
                isLeft = false;
            }
        }
        beforePos.set(currentPos);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    private void drawDefaultBound(float delta) {
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        this.shapeRenderer.end();
    }
}
