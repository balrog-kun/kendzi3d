/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.josm.kendzi3d.jogl.model.roof.mk.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import kendzi.jogl.model.factory.MaterialFactory;
import kendzi.jogl.model.factory.MeshFactory;
import kendzi.jogl.model.factory.ModelFactory;
import kendzi.jogl.model.geometry.Material;
import kendzi.josm.kendzi3d.dto.TextureData;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.RoofTextureData;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.RoofTypeOutput;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.dormer.space.RectangleRoofHooksSpaces;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.measurement.Measurement;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.measurement.MeasurementKey;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeAliasEnum;
import kendzi.math.geometry.Plane3d;
import kendzi.math.geometry.line.LinePoints2d;
import kendzi.math.geometry.polygon.MultiPolygonList2d;
import kendzi.math.geometry.polygon.PolygonList2d;

import org.apache.log4j.Logger;

/**
 * Roof type 4.2.
 *
 * @author Tomasz Kędziora (Kendzi)
 *
 */
public class RoofType4_2 extends RectangleRoofTypeBuilder{

    /** Log. */
    private static final Logger log = Logger.getLogger(RoofType4_2.class);

    @Override
    public RoofTypeAliasEnum getPrefixKey() {
        return RoofTypeAliasEnum.ROOF_TYPE4_2;
    }

    @Override
    public boolean isPrefixParameter() {
        return false;
    }

    @Override
    public RoofTypeOutput buildRectangleRoof(
            List<Point2d> border,
            Point2d[] rectangleContur,
            double pScaleA,
            double pScaleB,
            double pRecHeight,
            double pRecWidth,
            Integer prefixParameter,
            Map<MeasurementKey, Measurement> pMeasurements,
            RoofTextureData pRoofTextureData
            ) {

        Double h1 = getHeightMeters(pMeasurements, MeasurementKey.HEIGHT_1, 2.5d);
        Double h2 = getHeightMeters(pMeasurements, MeasurementKey.HEIGHT_2, h1 * 2d/3d);


//        Double l1 = getLenghtMetersPersent(pMeasurements, MeasurementKey.LENGTH_1, pRecWidth, pRecWidth / 2d);
        Double l2 = getLenghtMetersPersent(pMeasurements, MeasurementKey.LENGTH_2, pRecHeight /2, (pRecHeight / 2d) * 1d/3d);
//        Double l2 = getLenghtMetersPersent(pMeasurements, MeasurementKey.LENGTH_2, pRecHeight, pRecHeight / 2d);



        return build(border, pScaleA, pScaleB, pRecHeight, pRecWidth, rectangleContur, h1, h2, l2, pRoofTextureData);

    }

    @Override
    protected boolean normalizeAB() {
        return false;
    }

    /**
     *
     *
     *
     * @param pBorderList
     * @param pScaleA
     * @param pScaleB
     * @param pRecHeight
     * @param pRecWidth
     * @param pRectangleContur
     * @param h1
     * @param h2
     * @param l2
     * @param pRoofTextureData
     * @return
     */
    protected RoofTypeOutput build(
            List<Point2d> pBorderList,
            double pScaleA,
            double pScaleB,
            double pRecHeight,
            double pRecWidth,
            Point2d[] pRectangleContur,
            double h1,
            double h2,
            double l2,
            RoofTextureData pRoofTextureData) {


        ModelFactory model = ModelFactory.modelBuilder();
        MeshFactory meshBorder = model.addMesh("roof_border");
        MeshFactory meshRoof = model.addMesh("roof_top");

        //XXX move it
        TextureData facadeTexture = pRoofTextureData.getFacadeTextrure();
        TextureData roofTexture = pRoofTextureData.getRoofTexture();
        Material facadeMaterial = MaterialFactory.createTextureMaterial(facadeTexture.getFile());
        Material roofMaterial = MaterialFactory.createTextureMaterial(roofTexture.getFile());
        // XXX move material
        int facadeMaterialIndex = model.addMaterial(facadeMaterial);
        int roofMaterialIndex = model.addMaterial(roofMaterial);

        meshBorder.materialID = facadeMaterialIndex;
        meshBorder.hasTexture = true;

        meshRoof.materialID = roofMaterialIndex;
        meshRoof.hasTexture = true;

        double halfRecHeight = 0.5d * pRecHeight;

        Point2d rightTop = new Point2d(pRecWidth, pRecHeight);
        Point2d rightMiddleTopPoint = new Point2d(pRecWidth - l2, pRecHeight - l2);
        Point2d rightCenterPoint = new Point2d(pRecWidth - halfRecHeight, halfRecHeight);
        Point2d rightMiddleBottomPoint = new Point2d(pRecWidth - l2, l2);
        Point2d rightBottom = new Point2d(pRecWidth, 0);

        Point2d leftTop = new Point2d(0, pRecHeight);
        Point2d leftMiddleTopPoint = new Point2d(l2, pRecHeight - l2);
        Point2d leftCenterPoint = new Point2d(halfRecHeight, halfRecHeight);
        Point2d leftMiddleBottomPoint = new Point2d(l2, l2);
        Point2d leftBottom = new Point2d(0, 0);



        LinePoints2d tLine = new LinePoints2d(leftMiddleTopPoint, rightMiddleTopPoint);
        LinePoints2d mLine = new LinePoints2d(leftCenterPoint, rightCenterPoint);
        LinePoints2d bLine = new LinePoints2d(leftMiddleBottomPoint, rightMiddleBottomPoint);

        LinePoints2d lLine = new LinePoints2d(leftMiddleBottomPoint, leftMiddleTopPoint);
        LinePoints2d rLine = new LinePoints2d(rightMiddleTopPoint, rightMiddleBottomPoint);

        LinePoints2d lbLine = new LinePoints2d(leftBottom, leftCenterPoint);
        LinePoints2d ltLine = new LinePoints2d(leftCenterPoint, leftTop);

        LinePoints2d rtLine = new LinePoints2d(rightTop, rightCenterPoint);
        LinePoints2d rbLine = new LinePoints2d(rightCenterPoint, rightBottom);



        Vector3d nt = new Vector3d(0, l2  , -(h2));
        nt.normalize();
        Vector3d nmt = new Vector3d(0, (pRecHeight * 0.5d) - l2  , -(h1 - h2));
        nmt.normalize();

        Vector3d nmb = new Vector3d(0, (pRecHeight * 0.5d) - l2  , (h1 - h2));
        nmb.normalize();
        Vector3d nb = new Vector3d(0, l2  , (h2));
        nb.normalize();

        Vector3d nr = new Vector3d(h2, l2  , 0);
        nr.normalize();
        Vector3d nmr = new Vector3d((h1 - h2), (pRecHeight * 0.5d) - l2  , 0);
        nmr.normalize();
        Vector3d nl = new Vector3d(-h2, l2  , 0);
        nl.normalize();
        Vector3d nml = new Vector3d(-(h1 - h2), (pRecHeight * 0.5d) - l2  , 0);
        nml.normalize();


        PolygonList2d borderPolygon = new PolygonList2d(pBorderList);

        MultiPolygonList2d topMP = borderPolygon.intersection(
                new PolygonList2d(Arrays.asList(leftTop,  leftMiddleTopPoint, rightMiddleTopPoint, rightTop)));
        MultiPolygonList2d topMiddleMP = borderPolygon.intersection(
                new PolygonList2d(Arrays.asList(leftMiddleTopPoint, leftCenterPoint, rightCenterPoint, rightMiddleTopPoint)));
        MultiPolygonList2d bottomMiddleMP = borderPolygon.intersection(
                new PolygonList2d(Arrays.asList(leftMiddleBottomPoint, rightMiddleBottomPoint, rightCenterPoint, leftCenterPoint)));
        MultiPolygonList2d bottomMP = borderPolygon.intersection(
                new PolygonList2d(Arrays.asList(leftBottom, rightBottom, rightMiddleBottomPoint, leftMiddleBottomPoint)));

        MultiPolygonList2d leftMP = borderPolygon.intersection(
                new PolygonList2d(Arrays.asList(leftBottom,  leftMiddleBottomPoint, leftMiddleTopPoint, leftTop)));
        MultiPolygonList2d leftMiddleMP = borderPolygon.intersection(
                new PolygonList2d(Arrays.asList(leftMiddleBottomPoint,  leftCenterPoint, leftMiddleTopPoint)));

        MultiPolygonList2d rightMP = borderPolygon.intersection(
                new PolygonList2d(Arrays.asList(rightBottom, rightTop, rightMiddleTopPoint, rightMiddleBottomPoint)));
        MultiPolygonList2d rightMiddleMP = borderPolygon.intersection(
                new PolygonList2d(Arrays.asList(rightMiddleBottomPoint, rightMiddleTopPoint, rightCenterPoint)));



//        SplitPolygon middleSplit = PolygonSplitUtil.splitPolygon(borderPolygon, mLine);
//
//        MultiPolygonList2d topMP = middleSplit.getTopMultiPolygons();
//        MultiPolygonList2d bottomMP = middleSplit.getBottomMultiPolygons();
//
//
//        SplitPolygons topSplit = PolygonSplitUtil.splitMultiPolygon(topMP, tLine);
//
//        topMP = topSplit.getTopMultiPolygons();
//        MultiPolygonList2d topMiddleMP = topSplit.getBottomMultiPolygons();
//
//
//        SplitPolygons bottomSplit = PolygonSplitUtil.splitMultiPolygon(bottomMP, bLine);
//
//        bottomMP = bottomSplit.getBottomMultiPolygons();
//        MultiPolygonList2d bottomMiddleMP = bottomSplit.getTopMultiPolygons();


        Point3d planeLeftBottomPoint =  new Point3d(
                leftMiddleBottomPoint.x ,
                h2,
                -leftMiddleBottomPoint.y);

        Point3d planeRightTopPoint =  new Point3d(
                rightMiddleTopPoint.x ,
                h2,
                -rightMiddleTopPoint.y);



        Plane3d planeBottom = new Plane3d(
                planeLeftBottomPoint,
                nb);

        Plane3d planeMiddleBottom = new Plane3d(
                planeLeftBottomPoint,
                nmb);

        Plane3d planeTop = new Plane3d(
                planeRightTopPoint,
                nt);

        Plane3d planeMiddleTop = new Plane3d(
                planeRightTopPoint,
                nmt);


        Plane3d planeLeft = new Plane3d(
                planeLeftBottomPoint,
                nl);
        Plane3d planeMiddleLeft = new Plane3d(
                planeLeftBottomPoint,
                nml);

        Plane3d planeRight = new Plane3d(
                planeRightTopPoint,
                nr);
        Plane3d planeMiddleRight = new Plane3d(
                planeRightTopPoint,
                nmr);


        Vector3d roofBottomLineVector = new Vector3d(
                pRecWidth,
                0,
                0);

        Vector3d roofTopLineVector = new Vector3d(
                -pRecWidth,
                0,
                0);

        Vector3d roofLeftLineVector = new Vector3d(
                0,
                0,
                pRecHeight);

        Vector3d roofRightLineVector = new Vector3d(
                0,
                0,
                -pRecHeight);



        RoofTypeUtil.addPolygonToRoofMesh(meshRoof, topMP, planeTop, roofTopLineVector, roofTexture);
        RoofTypeUtil.addPolygonToRoofMesh(meshRoof, topMiddleMP, planeMiddleTop, roofTopLineVector, roofTexture);
        RoofTypeUtil.addPolygonToRoofMesh(meshRoof, bottomMiddleMP, planeMiddleBottom, roofBottomLineVector, roofTexture);
        RoofTypeUtil.addPolygonToRoofMesh(meshRoof, bottomMP, planeBottom, roofBottomLineVector, roofTexture);

        RoofTypeUtil.addPolygonToRoofMesh(meshRoof, leftMP, planeLeft, roofLeftLineVector, roofTexture);
        RoofTypeUtil.addPolygonToRoofMesh(meshRoof, leftMiddleMP, planeMiddleLeft, roofLeftLineVector, roofTexture);

        RoofTypeUtil.addPolygonToRoofMesh(meshRoof, rightMP, planeRight, roofRightLineVector, roofTexture);
        RoofTypeUtil.addPolygonToRoofMesh(meshRoof, rightMiddleMP, planeMiddleRight, roofRightLineVector, roofTexture);


        List<Point2d> borderSplit = RoofTypeUtil.splitBorder(borderPolygon, mLine, bLine, tLine,
                lLine, rLine, lbLine, ltLine, rtLine, rbLine
                );



        List<Double> borderHeights = calcHeightList(
                borderSplit, mLine, bLine, tLine, lLine, rLine, lbLine, ltLine, rtLine, rbLine,
                planeTop, planeMiddleTop, planeMiddleBottom, planeBottom,
                planeLeft, planeMiddleLeft, planeRight, planeMiddleRight
                );



        ////******************

        RoofTypeUtil.makeRoofBorderMesh(

               borderSplit,
               borderHeights,

               meshBorder,
               facadeTexture
               );



        RoofTypeOutput rto = new RoofTypeOutput();
        rto.setHeight(Math.max(h1, h2));

        rto.setModel(model);

        RectangleRoofHooksSpaces rhs =
            buildRectRoofHooksSpace(
                    pRectangleContur,
                    new PolygonPlane(bottomMP, planeBottom),
                    null,
                    new PolygonPlane(topMP, planeTop),
                    null
                  );

        rto.setRoofHooksSpaces(rhs);

        return rto;
    }



    private List<Double> calcHeightList(
            List<Point2d> pSplitBorder,
            LinePoints2d mLine, LinePoints2d bLine,  LinePoints2d tLine,
            LinePoints2d lLine, LinePoints2d rLine, LinePoints2d lbLine, LinePoints2d ltLine, LinePoints2d rtLine, LinePoints2d rbLine,
            Plane3d planeTop, Plane3d planeMiddleTop, Plane3d planeMiddleBottom, Plane3d planeBottom,
            Plane3d planeLeft, Plane3d planeMiddleLeft, Plane3d planeRight, Plane3d planeMiddleRight) {



        List<Double> borderHeights = new ArrayList<Double>(pSplitBorder.size());
        for (Point2d point : pSplitBorder) {

           double height = calcHeight(point, mLine, bLine, tLine,
                   lLine, rLine, lbLine, ltLine, rtLine, rbLine,
                   planeTop, planeMiddleTop, planeMiddleBottom, planeBottom,
                   planeLeft, planeMiddleLeft, planeRight, planeMiddleRight
                   );

           borderHeights.add(height);

        }

        return borderHeights;
    }

    /** Calc height of point in border.
     * @param point
     * @param mLine
     * @param bLine
     * @param tLine
     * @param planeTop
     * @param planeMiddleTop
     * @param planeMiddleBottom
     * @param planeBottom
     * @return
     */
    private double calcHeight(Point2d point,
            LinePoints2d mLine, LinePoints2d bLine,  LinePoints2d tLine,
            LinePoints2d lLine, LinePoints2d rLine, LinePoints2d lbLine, LinePoints2d ltLine, LinePoints2d rtLine, LinePoints2d rbLine,
            Plane3d planeTop, Plane3d planeMiddleTop, Plane3d planeMiddleBottom, Plane3d planeBottom,
            Plane3d planeLeft, Plane3d planeMiddleLeft, Plane3d planeRight, Plane3d planeMiddleRight) {


        double x = point.x;
        double z = -point.y;

        if (lbLine.inFront(point) && ltLine.inFront(point)) {
            if (lLine.inFront(point)) {
                return planeLeft.calcYOfPlane(x, z);
            }
            return planeMiddleLeft.calcYOfPlane(x, z);
        } else if (rtLine.inFront(point) && rbLine.inFront(point)) {
            if (rLine.inFront(point)) {
                return planeRight.calcYOfPlane(x, z);
            }
            return planeMiddleRight.calcYOfPlane(x, z);

        } else if (mLine.inFront(point)) {

            if (tLine.inFront(point)) {
                return planeTop.calcYOfPlane(x, z);
            }
            return planeMiddleTop.calcYOfPlane(x, z);

        } else {

            if (bLine.inFront(point)) {
                return planeMiddleBottom.calcYOfPlane(x, z);
            }
            return planeBottom.calcYOfPlane(x, z);
        }
    }

}
