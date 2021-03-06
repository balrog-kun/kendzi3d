/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */


package kendzi.josm.kendzi3d.action;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;

import kendzi.josm.kendzi3d.jogl.Camera;
import kendzi.josm.kendzi3d.jogl.RenderJOSM;
import kendzi.josm.kendzi3d.ui.Kendzi3dGLEventListener;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.JosmAction;

import com.google.inject.Inject;

/**
 * Move camera action.
 *
 * @author Tomasz Kędziora (Kendzi)
 *
 */
public class MoveCameraAction extends JosmAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private RenderJOSM renderJosm;

    private Kendzi3dGLEventListener kendzi3dGLEventListener;



    /**
     * Constructor.
     * @param renderJosm
     * @param kendzi3dGLEventListener
     */
    @Inject
    public MoveCameraAction(RenderJOSM renderJosm, Kendzi3dGLEventListener kendzi3dGLEventListener) {
        super(
                tr("Move camera"),
                "1306318146_build__24",
                tr("Move camera"),
                null,
                false
        );

        this.renderJosm = renderJosm;
        this.kendzi3dGLEventListener = kendzi3dGLEventListener;
    }

    @Override
    public void actionPerformed(ActionEvent pE) {

        double x = this.renderJosm.getPerspective().calcX(Main.map.mapView.getCenter().getX());
        double y = this.renderJosm.getPerspective().calcY(Main.map.mapView.getCenter().getY());

        this.kendzi3dGLEventListener.setCamPos(x, Camera.CAM_HEIGHT, -y);
    }

    @Override
    protected void updateEnabledState() {
//        setEnabled(Main.map != null && Main.main.getEditLayer() != null);
    }
}
