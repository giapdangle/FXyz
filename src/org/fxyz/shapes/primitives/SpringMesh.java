package org.fxyz.shapes.primitives;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.DepthTest;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.TriangleMesh;
import org.fxyz.geometry.Point3D;

/**
 *  Spring based on this model:  http://math.stackexchange.com/a/461637
 */
public class SpringMesh extends TexturedMesh {

    private static final double DEFAULT_MEAN_RADIUS = 10.0D;
    private static final double DEFAULT_WIRE_RADIUS = 0.2D;
    private static final double DEFAULT_PITCH = 5.0D;
    private static final double DEFAULT_LENGTH = 100.0D;
    
    private static final int DEFAULT_LENGTH_DIVISIONS = 200;
    private static final int DEFAULT_WIRE_DIVISIONS = 50;
    private static final int DEFAULT_LENGTH_CROP = 0;
    private static final int DEFAULT_WIRE_CROP = 0;
    
    private static final double DEFAULT_START_ANGLE = 0.0D;
    private static final double DEFAULT_X_OFFSET = 0.0D;
    private static final double DEFAULT_Y_OFFSET = 0.0D;
    private static final double DEFAULT_Z_OFFSET = 1.0D;
    
    
    public SpringMesh() {
        this(DEFAULT_MEAN_RADIUS, DEFAULT_WIRE_RADIUS, DEFAULT_PITCH, DEFAULT_LENGTH,
             DEFAULT_LENGTH_DIVISIONS, DEFAULT_WIRE_DIVISIONS, DEFAULT_LENGTH_CROP, DEFAULT_WIRE_CROP);
    }

    public SpringMesh(double meanRadius, double wireRadius, double pitch, double length) {
        this(meanRadius, wireRadius, pitch, length, 
             DEFAULT_LENGTH_DIVISIONS, DEFAULT_WIRE_DIVISIONS, DEFAULT_LENGTH_CROP, DEFAULT_WIRE_CROP);
    }

    double factor=1d;
    
    public SpringMesh(double meanRadius, double wireRadius, double pitch, double length, 
                      int rDivs, int tDivs, int lengthCrop, int wireCrop) {
        
        setMeanRadius(meanRadius);
        setWireRadius(wireRadius);
        setPitch(pitch);
        setLength(length);
        factor=length/pitch;
        setLengthDivisions(rDivs);
        setWireDivisions(tDivs);
        setLengthCrop(lengthCrop);
        setWireCrop(wireCrop);
        
        updateMesh();
        setCullFace(CullFace.BACK);
        setDrawMode(DrawMode.FILL);
        setDepthTest(DepthTest.ENABLE);
        
    }

    @Override
    protected final void updateMesh(){   
        setMesh(null);
        mesh=createSpring((float) getMeanRadius(), (float) getWireRadius(), (float) getPitch(), (float) getLength(),
            getLengthDivisions(), getWireDivisions(), getLengthCrop(), getWireCrop(),
            (float) getTubeStartAngleOffset(), (float)getxOffset(),(float)getyOffset(), (float)getzOffset());
        setMesh(mesh);
        
    }
    
    private final DoubleProperty meanRadius = new SimpleDoubleProperty(DEFAULT_MEAN_RADIUS){

        @Override protected void invalidated() {
            if(mesh!=null){
                updateMesh();
            }
        }
    };

    public final double getMeanRadius() {
        return meanRadius.get();
    }

    public final void setMeanRadius(double value) {
        meanRadius.set(value);
    }

    public DoubleProperty meanRadiusProperty() {
        return meanRadius;
    }

    
    private final DoubleProperty wireRadius = new SimpleDoubleProperty(DEFAULT_WIRE_RADIUS){

        @Override protected void invalidated() {
            if(mesh!=null){
                updateMesh();
            }
        }
    };

    public final double getWireRadius() {
        return wireRadius.get();
    }

    public final void setWireRadius(double value) {
        wireRadius.set(value);
    }

    public DoubleProperty wireRadiusProperty() {
        return wireRadius;
    }

    private final DoubleProperty length = new SimpleDoubleProperty(DEFAULT_LENGTH){

        @Override protected void invalidated() {
            if(mesh!=null){
                setPitch(length.get()/factor);
                updateMesh();
            }
        }
    };

    public final double getLength() {
        return length.get();
    }

    public final void setLength(double value) {
        length.set(value);
    }

    public DoubleProperty lengthProperty() {
        return length;
    }

    private final DoubleProperty pitch = new SimpleDoubleProperty(DEFAULT_PITCH){

        @Override protected void invalidated() {
            if(mesh!=null){
                setLength(pitch.get()*factor);
                updateMesh();
            }
        }
    };

    public final double getPitch() {
        return pitch.get();
    }

    public final void setPitch(double value) {
        pitch.set(value);
    }

    public DoubleProperty pitchProperty() {
        return pitch;
    }
    
    private final IntegerProperty lengthDivisions = new SimpleIntegerProperty(DEFAULT_LENGTH_DIVISIONS){

        @Override protected void invalidated() {
            if(mesh!=null){
                updateMesh();
            }
        }
    };

    public final int getLengthDivisions() {
        return lengthDivisions.get();
    }

    public final void setLengthDivisions(int value) {
        lengthDivisions.set(value);
    }

    public IntegerProperty lengthDivisionsProperty() {
        return lengthDivisions;
    }

    private final IntegerProperty wireDivisions = new SimpleIntegerProperty(DEFAULT_WIRE_DIVISIONS){

        @Override protected void invalidated() {
            if(mesh!=null){
                updateMesh();
            }
        }
    };

    public final int getWireDivisions() {
        return wireDivisions.get();
    }

    public final void setWireDivisions(int value) {
        wireDivisions.set(value);
    }

    public IntegerProperty wireDivisionsProperty() {
        return wireDivisions;
    }

    private final IntegerProperty lengthCrop = new SimpleIntegerProperty(DEFAULT_LENGTH_CROP){

        @Override protected void invalidated() {
            if(mesh!=null){
                updateMesh();
            }
        }
    };
    
    public final int getLengthCrop() {
        return lengthCrop.get();
    }

    public final void setLengthCrop(int value) {
        lengthCrop.set(value);
    }

    public IntegerProperty lengthCropProperty() {
        return lengthCrop;
    }

    private final IntegerProperty wireCrop = new SimpleIntegerProperty(DEFAULT_WIRE_CROP){

        @Override protected void invalidated() {
            if(mesh!=null){
                updateMesh();
            }
        }
    };
    
    public final int getWireCrop() {
        return wireCrop.get();
    }

    public final void setWireCrop(int value) {
        wireCrop.set(value);
    }

    public IntegerProperty wireCropProperty() {
        return wireCrop;
    }
    
    private final DoubleProperty tubeStartAngleOffset = new SimpleDoubleProperty(DEFAULT_START_ANGLE){

        @Override protected void invalidated() {
            if(mesh!=null){
                updateMesh();
            }
        }
    };

    public final double getTubeStartAngleOffset() {
        return tubeStartAngleOffset.get();
    }

    public void setTubeStartAngleOffset(double value) {
        tubeStartAngleOffset.set(value);
    }

    public DoubleProperty tubeStartAngleOffsetProperty() {
        return tubeStartAngleOffset;
    }
    private final DoubleProperty xOffset = new SimpleDoubleProperty(DEFAULT_X_OFFSET){

        @Override
        protected void invalidated() {
            if(mesh!=null){
                updateMesh();
            }
        }
        
    };

    public final double getxOffset() {
        return xOffset.get();
    }

    public void setxOffset(double value) {
        xOffset.set(value);
    }

    public DoubleProperty xOffsetProperty() {
        return xOffset;
    }
    private final DoubleProperty yOffset = new SimpleDoubleProperty(DEFAULT_Y_OFFSET){

        @Override
        protected void invalidated() {
            if(mesh!=null){
                updateMesh();
            }
        }
        
    };

    public final double getyOffset() {
        return yOffset.get();
    }

    public void setyOffset(double value) {
        yOffset.set(value);
    }

    public DoubleProperty yOffsetProperty() {
        return yOffset;
    }
    private final DoubleProperty zOffset = new SimpleDoubleProperty(DEFAULT_Z_OFFSET){

        @Override
        protected void invalidated() {
            if(mesh!=null){
                updateMesh();
            }
        }
        
    };

    public final double getzOffset() {
        return zOffset.get();
    }

    public void setzOffset(double value) {
        zOffset.set(value);
    }

    public DoubleProperty zOffsetProperty() {
        return zOffset;
    }
    
    private TriangleMesh createSpring(float meanRadius, float wireRadius, float pitch, float length, 
            int subDivLength, int subDivWire, int cropLength, int cropWire,
            float startAngle, float xOffset, float yOffset, float zOffset) {
 
        listVertices.clear();
        listTextures.clear();
        listFaces.clear();
        
        int numDivLength = subDivLength + 1-2*cropLength;
        float pointX, pointY, pointZ;
        double R=meanRadius;
        double h=pitch;
        double a=wireRadius;
        
        float norm=(float)Math.sqrt(h*h+R*R);
        areaMesh.setWidth(norm * length/pitch);
        areaMesh.setHeight(2*Math.PI*wireRadius);
        // Create points
        for (int u = cropWire; u <= subDivWire-cropWire; u++) { // -Pi - +Pi
            float du = (float) (((double)u)*2d*Math.PI / ((double)subDivWire));
            double pol = polygonalSection(du);
            double cdu=pol*Math.cos(du), sdu=pol*Math.sin(du); 
            for (int t = cropLength; t <= subDivLength-cropLength; t++) {  // 0 - length
                if(cropWire>0 || (cropWire==0 && u<subDivWire)){
                    float dt = ((float) t / ((float)subDivLength)) * length/pitch;
                    double cdt=Math.cos(dt), sdt=Math.sin(dt);
                    pointX = (float) (R*cdt-a*cdt*cdu+a*h*sdt*sdu/norm);
                    pointY = (float) (R*sdt-a*sdt*cdu-a*h*cdt*sdu/norm);
                    pointZ = (float) (h*dt+a*R*sdu/norm);
                    listVertices.add(new Point3D(pointX, pointY, pointZ));
                }
            }
        }
        // Create texture coordinates
        createTexCoords(subDivLength-2*cropLength,subDivWire-2*cropWire);

        // Create textures
        for (int u = cropWire; u < subDivWire-cropWire; u++) { // -Pi - +Pi
            for (int t = cropLength; t < subDivLength-cropLength; t++) { // 0 - length
                int p00 = (u-cropWire) * numDivLength + (t-cropLength);
                int p01 = p00 + 1;
                int p10 = p00 + numDivLength;
                int p11 = p10 + 1;                
                listTextures.add(new Point3D(p00,p10,p11));
                listTextures.add(new Point3D(p11,p01,p00));            
            }
        }
        // Create faces
        for (int u = cropWire; u < subDivWire-cropWire; u++) { // -Pi - +Pi
            for (int t = cropLength; t < subDivLength-cropLength; t++) { // 0 - length
                int p00 = (u-cropWire) * numDivLength + (t-cropLength);
                int p01 = p00 + 1;
                int p10 = p00 + numDivLength;
                if(cropWire==0 && u==subDivWire-1){
                    p10-=subDivWire*numDivLength;
                }
                int p11 = p10 + 1;                
                listFaces.add(new Point3D(p00,p10,p11));
                listFaces.add(new Point3D(p11,p01,p00));            
            }
        }
        return createMesh();
    }
    
}
