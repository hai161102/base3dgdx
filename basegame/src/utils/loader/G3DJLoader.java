package utils.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.utils.BaseJsonReader;
import com.badlogic.gdx.utils.JsonReader;

public class G3DJLoader extends G3dModelLoader {

    private static G3DJLoader instance;
    private G3DJLoader(BaseJsonReader reader) {
        super(reader);
    }

    private G3DJLoader(BaseJsonReader reader, FileHandleResolver resolver) {
        super(reader, resolver);
    }

    public static G3DJLoader getInstance() {
        if (instance == null) instance = new G3DJLoader(new JsonReader());
        return instance;
    }

    public Model loadModel(String path) {
        return this.loadModel(Gdx.files.internal(path));
    }
}
