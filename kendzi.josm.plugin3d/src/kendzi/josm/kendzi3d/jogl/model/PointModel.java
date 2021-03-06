/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.josm.kendzi3d.jogl.model;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import javax.media.opengl.GL2;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import kendzi.jogl.model.geometry.Material;
import kendzi.jogl.model.geometry.Model;
import kendzi.jogl.model.render.ModelRender;
import kendzi.josm.kendzi3d.jogl.Camera;
import kendzi.josm.kendzi3d.jogl.layer.PointModelsLayer.PointModelConf;
import kendzi.josm.kendzi3d.jogl.model.export.ExportItem;
import kendzi.josm.kendzi3d.jogl.model.export.ExportModelConf;
import kendzi.josm.kendzi3d.jogl.model.lod.DLODSuport;
import kendzi.josm.kendzi3d.jogl.model.lod.LOD;
import kendzi.josm.kendzi3d.jogl.model.tmp.AbstractPointModel;
import kendzi.josm.kendzi3d.service.ModelCacheService;
import kendzi.josm.kendzi3d.util.expression.Context;
import kendzi.josm.kendzi3d.util.expression.fun.SimpleFunction;
import net.java.joglutils.model.ModelLoadException;

import org.apache.log4j.Logger;
import org.openstreetmap.josm.data.osm.Node;

/**
 * Model builder for objects loaded from obj files.
 *
 * @author Tomasz Kędziora (kendzi)
 *
 */
public class PointModel extends AbstractPointModel implements DLODSuport {

    /** Log. */
    private static final Logger log = Logger.getLogger(PointModel.class);

    /**
     * Model renderer.
     */
    private ModelRender modelRenderer;

    ModelCacheService modelCacheService;

    /**
     * Lod support.
     */
    private EnumMap<LOD, Model> modelLod;

    /**
     * Model scale;
     */
    private Vector3d scale;

    /**
     * Model translation.
     */
    private Vector3d translate;

    /**
     * Model configuration.
     */
    private PointModelConf pointModelConf;

    /** Constructor.
     * @param node node
     * @param pPointModelConf model configuration
     * @param pPerspective3D perspective 3d
     */
    public PointModel(Node node, PointModelConf pPointModelConf, Perspective3D pPerspective3D,
            ModelRender pModelRender,
            ModelCacheService modelCacheService) {
        super(node, pPerspective3D);

        this.modelLod = new EnumMap<LOD, Model>(LOD.class);

        this.scale = new Vector3d(1d, 1d, 1d);

        this.modelRenderer = pModelRender;

        this.pointModelConf = pPointModelConf;
        this.modelCacheService = modelCacheService;
    }

    @Override
    public void buildModel() {

        buildModel(LOD.LOD1);

        this.buildModel = true;
    }

    @Override
    public void buildModel(LOD pLod) {

        Model model = getModel(this.pointModelConf, pLod, this.modelCacheService);

        double scale = 1d;

        SimpleFunction scaleFun = this.pointModelConf.getScale();
        if (scaleFun != null) {

            Context context = new Context();
            context.putVariable("osm", this.node);
            context.putVariable("normal", getModelNormal(model));

            scale = scaleFun.eval(context);
        }

        this.scale = new Vector3d(scale, scale, scale);

        this.translate = this.pointModelConf.getTranslate();

        this.modelLod.put(pLod, model);
    }

    private Double getModelNormal(Model pModel) {
        if (pModel == null) {
            return null;
        }
        return 1d / (pModel.getBounds().max.y - pModel.getBounds().min.y);
    }

    private static Model getModel(PointModelConf pointModelConf, LOD pLod, ModelCacheService modelCacheService) {
        if (pointModelConf == null) {
            return null;
        }
        String key = pointModelConf.getModel();
        try {
            Model loadModel = modelCacheService.loadModel(key);
            loadModel.useLight = true;
            setAmbientColor(loadModel);
            return loadModel;

        } catch (ModelLoadException e) {
            log.error("error loading model file: " + key, e);
        }
        return null;
    }

    @Override
    public boolean isModelBuild(LOD pLod) {

        if (this.modelLod.get(pLod) != null) {
            return true;
        }
        return false;
    }

    private static void setAmbientColor(Model pModel) {
        for (int i = 0; i < pModel.getNumberOfMaterials(); i++) {
            Material material = pModel.getMaterial(i);
            material.ambientColor = material.diffuseColor;
        }
    }

    @Override
    public void draw(GL2 gl, Camera camera, LOD pLod) {
        Model model2 = this.modelLod.get(pLod);
        if (model2 != null) {

            gl.glPushMatrix();
            gl.glTranslated(this.getGlobalX(), 0, -this.getGlobalY());

            gl.glEnable(GL2.GL_NORMALIZE); //XXX
            gl.glScaled(this.scale.x, this.scale.y, this.scale.z);
            gl.glTranslated(this.translate.x, this.translate.y, this.translate.z);

            this.modelRenderer.render(gl, model2);

            gl.glDisable(GL2.GL_NORMALIZE);

            gl.glPopMatrix();
        }
    }

    @Override
    public void draw(GL2 gl, Camera camera) {
        draw(gl, camera, LOD.LOD1);
    }

    @Override
    public Point3d getPoint() {
        return new Point3d(this.x, 0, -this.y);
    }

    @Override
    public List<ExportItem> export(ExportModelConf conf) {
        if (this.modelLod.get(LOD.LOD1) == null) {
            buildModel(LOD.LOD1);
        }

        return Collections.singletonList(new ExportItem(this.modelLod.get(LOD.LOD1), new Point3d(this.getGlobalX(), 0, -this.getGlobalY()), new Vector3d(1,1,1)));
    }
}
