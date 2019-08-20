
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;

public class LifeStatus implements TDDrawable {
	private int points = 20;
	
	public LifeStatus() {}
	
	
	/**
	 * this draw is in 2D!
	 * max hp 100 = 20 pieces.
	 * will be draw on the left top of the screen
	 * @param gl
	 */
	@Override
	public void draw2D(GL2 gl) {
		//the separating space
		float sep = 0.03f;
		
		// hp piece width
		int numDiv = 12;
		float barWidth = 1.0f/(float)numDiv;
		
		//set the counter to draw the hp and the x value
		int hpCounter = 0;
		float i = -0.08f;
		
		// colouring for this particular object material
		gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, new float[]{0.8f, 0.0f, 0.0f, 1.0f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, new float[]{1.0f, 0.0f, 0.0f, 1.0f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, new float[]{0.5f, 0.0f, 0.0f, 1.0f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, new float[]{60.0f}, 0);

        gl.glColor3f(1.0f, 0.0f, 0.0f);
		
		gl.glBegin(GL2GL3.GL_QUADS);
		
		//draw all the hp pieces
		while (hpCounter < points) {
			gl.glColor3d(1,0,0);
			gl.glVertex2f(i, 0.1f);
			gl.glColor3d(1,1,0);
			gl.glVertex2f(i, -0.3f);
			gl.glColor3d(1,1,1);
			gl.glVertex2f(i + barWidth, -0.3f);
			gl.glColor3d(0,1,1);
		    gl.glVertex2f(i + barWidth, 0.1f);
		    
			i += (sep + barWidth);
			hpCounter++;
		}
		gl.glEnd();
		
		// material properties back to default
		gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, new float[]{0.8f, 0.8f, 0.8f, 1.0f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, new float[]{0.8f, 0.8f, 0.8f, 1.0f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, new float[]{0.0f, 0.0f, 0.0f, 1.0f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, new float[]{0.2f, 0.2f, 0.2f, 0.2f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, new float[]{50.0f}, 0);
	}
	
	/**
	 * update the points point if got damage
	 * @param dmg
	 */
	public void decreaseHP(int dmg) {
		if (points > 0) {
			points -= dmg;
			if(points < 0) {
				points = 0;
			}
		}
	}
	public void increaseHP(int hp) {
		if (points < 20) {
			points += hp;
			if (points > 20) {
				points = 20;
			}
		}	
	}
	
	public void initHealthPoints() {
		points = 20;
	}
	public int getHealthPoints() {
		return points;
	}
}
