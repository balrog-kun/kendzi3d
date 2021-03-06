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

import kendzi.josm.kendzi3d.jogl.RenderJOSM;
import kendzi.josm.kendzi3d.service.TextureCacheService;
import kendzi.josm.kendzi3d.service.TextureLibraryService;

import org.openstreetmap.josm.actions.JosmAction;

import com.google.inject.Inject;

/**
 * Clean up action.
 *
 * @author Tomasz Kędziora (Kendzi)
 *
 */
public class CleanUpAction extends JosmAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * JOSM 3D Render.
     */
    private RenderJOSM renderJosm;

    /**
     * Texture cache service.
     */
    private TextureCacheService textureCacheService;

    /**
     * Texture library service.
     */
    private TextureLibraryService textureLibraryService;

    /**
     * Constructor.
     * @param renderJosm
     * @param textureCacheService
     */
    @Inject
    public CleanUpAction(RenderJOSM renderJosm,
            TextureCacheService textureCacheService,
            TextureLibraryService textureLibraryService) {
        super(
                tr("Clean up"),
                "1306318208_rebuild__24",
                tr("Rebuild models, textures and wold offset"),
                null,
                false
        );

        this.renderJosm = renderJosm;
        this.textureCacheService = textureCacheService;
        this.textureLibraryService = textureLibraryService;
    }

    @Override
    public void actionPerformed(ActionEvent pE) {

        this.textureLibraryService.clear();

        this.textureCacheService.clear();

        // XXX add event
        this.renderJosm.processDatasetEvent(null);
    }

    @Override
    protected void updateEnabledState() {
//        setEnabled(Main.map != null && Main.main.getEditLayer() != null);
    }
}
