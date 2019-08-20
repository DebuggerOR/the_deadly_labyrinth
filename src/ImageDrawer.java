
import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;


// draw an image in 2d given list of vertices
public class ImageDrawer implements TDDrawable {
	private String fileName;
	private float[][] vertices;
	private Texture texture;
	
	public ImageDrawer(String imageFileName, float[][] ImageVertices) {
		fileName = imageFileName;
		vertices = ImageVertices;
	}
	
	public void loadImage() {
		try {
    		texture = TextureIO.newTexture(new File(fileName),true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
	}

	@Override
	public void draw2D(GL2 gl) {
	    // change colouring for this particular object material
	    gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
	    gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
	    gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
	    gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
	    gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, new float[]{60.0f}, 0);
	  
	    // set texture to image
	    gl.glTexParameteri ( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP );
	    gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP );
	    gl.glActiveTexture(GL2.GL_TEXTURE0);
	    texture.enable(gl);
	    texture.bind(gl);
	          
	    // draw a quad and set the texture
	    gl.glBegin(GL2GL3.GL_QUADS);
	    
	    gl.glTexCoord2f(0f, 1f); 
	    gl.glVertex2f(vertices[0][0], vertices[0][1]);
	    gl.glTexCoord2f(0f, 0f); 
	    gl.glVertex2f(vertices[1][0], vertices[1][1]);
	    gl.glTexCoord2f(1f, 0f); 
	    gl.glVertex2f(vertices[2][0], vertices[2][1]);
	    gl.glTexCoord2f(1f, 1f); 
	    gl.glVertex2f(vertices[3][0], vertices[3][1]);
	    
	    gl.glEnd();
	    
	    texture.disable(gl);
	  
	    // material properties back to default
	    gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, new float[]{0.8f, 0.8f, 0.8f, 1.0f}, 0);
	    gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, new float[]{0.8f, 0.8f, 0.8f, 1.0f}, 0);
	    gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, new float[]{0.0f, 0.0f, 0.0f, 1.0f}, 0);
	    gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, new float[]{0.2f, 0.2f, 0.2f, 0.2f}, 0);
	    gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, new float[]{50.0f}, 0);
	}
}
