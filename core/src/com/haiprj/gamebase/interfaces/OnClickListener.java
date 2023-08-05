package com.haiprj.gamebase.interfaces;

import com.badlogic.gdx.math.Vector3;
import com.haiprj.gamebase.base.model.BaseModel;

public interface OnClickListener {
    void onClick(BaseModel baseModel, Vector3 clickPosition);
    void onDrag(BaseModel baseModel, Vector3 defaultPosition);

}
