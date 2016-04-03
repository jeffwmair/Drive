package com.jwm.j3dfw.geometry;

import com.jwm.j3dfw.production.Camera;

/**
 * Responsible for creating all the geometry objects
 * Created by Jeff on 2016-04-02.
 */
public interface GeometryFactory {

    GeometryList buildGeometryItems();
    Camera getMainCamera();
}
