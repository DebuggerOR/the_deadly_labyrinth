
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;


// object in our game
public class GameObj extends MazeObject {
    private float yrot = 30;
    private float xtran = 0.0f;
    private boolean move = false;

    public enum ObjType {START, HEAL, DAMAGE, ENDKEY, END, NONE}

    private Texture obj_texture;
    private WavefrontObjectLoader obj;

    //if to draw the obj or not
    boolean draw = true;

    //The variable that decides the type of the object
    private ObjType type;

    public GameObj(ObjType type) {
        this.type = type;
        setObjects(type);
    }

    /**
     * sets object's type
     */
    public ObjType getObjType() {
        return type;
    }

    /**
     * checks if start position
     */
    public boolean isStart() {
        if (type == ObjType.START) {
            return true;
        }
        return false;
    }

    /**
     * stop drawing object after hit
     */
    public void stopDraw() {
        draw = false;
    }

    /**
     * draw object
     */
    public void draw(GL2 gl) {
        if (!draw) {
            return;
        }

        GLUT glut = new GLUT();

        //Transform the to-be-placed geometric shapes
        gl.glPushMatrix();
        gl.glRotatef(90, -1.0f, 0.0f, 0.0f);
        gl.glTranslatef(0.0f, 0.0f, -0.35f);

        //Check which object this and draw accordingly
        switch (type) {
            case START: {
                // set colouring for start cone
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, new float[]{1.0f, 0.9f, 0.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, new float[]{0.8f, 0.8f, 0.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, new float[]{1.0f, 0.8f, 0.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, new float[]{1.0f, 0.8f, 0.2f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, new float[]{10.0f}, 0);

                gl.glTranslatef(0.0f, 0.0f, -0.1f);
                gl.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);

                gl.glColor3f(1.0f, 0.0f, 0.0f);

                glut.glutSolidCone(0.2f, 0.05f, 15, 15);
                break;
            }
            case END: {
                // set colouring for end torus
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, new float[]{0.2f, 0.2f, 0.2f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, new float[]{0.6f, 0.6f, 0.8f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, new float[]{0.3f, 0.0f, 0.5f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, new float[]{50.0f}, 0);

                gl.glColor3f(0.0f, 1.0f, 0.0f);

                gl.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
                gl.glTranslatef(0.0f, 0.0f, 0.0f);
                gl.glTranslatef(xtran, 0.0f, 0.0f);

                if (xtran > -0.0009f) {
                    move = false;
                }
                if (xtran < -0.02) {
                    move = true;
                }
                if (move) {
                    xtran += 0.001f;
                } else {
                    xtran -= 0.001f;
                }

                glut.glutSolidTorus(0.05f, 0.1f, 15, 15);
                break;
            }
            case HEAL: {
                // set colouring for healing teapot
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, new float[]{0.0f, 0.2f, 1.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, new float[]{0.0f, 0.8f, 1.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, new float[]{0.0f, 0.1f, 0.3f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, new float[]{1.0f, 0.6f, 0.6f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, new float[]{50.0f}, 0);

                gl.glColor3f(0.0f, 1.0f, 1.0f);

                gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                gl.glTranslatef(0.0f, 0.1f + xtran, 0.0f);

                if (xtran > -0.0009f) {
                    move = false;
                }
                if (xtran < -0.05) {
                    move = true;
                }
                if (move) {
                    xtran += 0.001f;
                } else {
                    xtran -= 0.001f;
                }

                glut.glutSolidTeapot(0.15f);
                break;
            }
            case DAMAGE: {
                // set colouring for hurting cylinder
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, new float[]{1.0f, 0.2f, 0.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, new float[]{1.0f, 0.2f, 1.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, new float[]{1.0f, 0.2f, 0.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, new float[]{1.0f, 0.1f, 0.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, new float[]{60.0f}, 0);

                gl.glRotatef(yrot, 0.0f, 0.0f, 1.0f);
                gl.glTranslatef(0.0f, 0.0f, -0.19f);

                yrot += 3.5f;
                if (yrot > 390) {
                    yrot = 30;
                }

                gl.glColor3f(1.0f, 0.0f, 1.0f);
                glut.glutSolidCylinder(0.28f, 0.14f, 20, 5);

                break;
            }
            case ENDKEY: {
                // set colouring for key
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
                gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, new float[]{20.0f}, 0);

                gl.glColor3f(1.0f, 1.0f, 1.0f);
                // set the texture for the object
                gl.glActiveTexture(GL2.GL_TEXTURE0);
                obj_texture.enable(gl);
                obj_texture.bind(gl);

                yrot += 2.5f;
                if (yrot > 390) {
                    yrot = 30;
                }
                gl.glRotatef(-70.0f, 0.0f, 1.0f, 0.0f);
                gl.glRotatef(yrot, 1.0f, 0.0f, 0.0f);
                gl.glScalef(0.02f, 0.02f, 0.02f);
                obj.drawModel(gl);
                obj_texture.disable(gl);

                break;
            }
            default:
                break;
        }

        // change material properties back to default
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, new float[]{0.8f, 0.8f, 0.8f, 1.0f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, new float[]{0.8f, 0.8f, 0.8f, 1.0f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, new float[]{0.0f, 0.0f, 0.0f, 1.0f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, new float[]{0.2f, 0.2f, 0.2f, 0.2f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, new float[]{50.0f}, 0);

        gl.glPopMatrix();
    }


    private void setObjects(ObjType type) {
        switch (type) {
            case DAMAGE:/*
    		try {
        		obj_texture=TextureIO.newTexture(new File( "textures/axe.jpg" ),true);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    		
            obj = new WavefrontObjectLoader("resources/axe_v1.obj");*/
                break;

            case ENDKEY: {
                try {
                    obj_texture = TextureIO.newTexture(new File("textures/keyB_tx.bmp"), true);
                } catch (IOException ignored) {
                }
                obj = new WavefrontObjectLoader("resources/Key_B_02.obj");

                break;
            }
            default:
                break;
        }

    }
}
