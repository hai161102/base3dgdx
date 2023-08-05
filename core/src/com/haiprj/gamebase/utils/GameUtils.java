package com.haiprj.gamebase.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.haiprj.gamebase.base.model.BaseModel;

public class GameUtils {
    public static float CONFIG_SIZE = 0.1f;
    public static float VIEWPORT_WIDTH = Gdx.graphics.getWidth() / 2f;
    public static float VIEWPORT_HEIGHT = Gdx.graphics.getHeight() / 2f;

    public static Vector3 getWorldPositionOnClick(Camera camera, ModelInstance modelInstance, Vector2 point) {

        Vector3 screenCoordinates = new Vector3(point.x, point.y, 0);
        Vector3 worldCoordinates = new Vector3();

        // Un project the screen click to world coordinates
        camera.unproject(screenCoordinates);

        // Create a ray from the camera through the clicked point
        Ray ray = new Ray(camera.position, worldCoordinates.set(screenCoordinates).sub(camera.position).nor());

        // Perform ray casting and collision detection
        Vector3 intersectionPoint = new Vector3();
        if (Intersector.intersectRayBounds(ray, modelInstance.calculateBoundingBox(new BoundingBox()), intersectionPoint)) {
            return intersectionPoint;
        }
        return null;
    }

    public static Vector3 getWorldPositionOnClick(Camera camera, ModelInstance baseModel, int x, int y) {
        return getWorldPositionOnClick(camera, baseModel, new Vector2(x, y));
    }
}
