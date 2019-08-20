package Collision;

public class AABB extends Shape {
	private float xMax;
	private float yMax;
	private float zMax;
	private float xMin;
	private float yMin;
	private float zMin;
	
	public AABB(float xMax, float yMax, float zMax, float xMin, float yMin, float zMin) {
		this.xMax = xMax;
		this.yMax = yMax;
		this.zMax = zMax;
		this.xMin = xMin;
		this.yMin = yMin;
		this.zMin = zMin;
	}

	public float getZMin() {
		return this.zMin;
	}

	public void setZMin(float zMin) {
		this.zMin = zMin;
	}

	public float getYMin() {
		return this.yMin;
	}

	public void setYMin(float yMin) {
		this.yMin = yMin;
	}

	public float getXMin() {
		return this.xMin;
	}

	public void setXMin(float xMin) {
		this.xMin = xMin;
	}

	public float getZMax() {
		return this.zMax;
	}

	public void setZMax(float zMax) {
		this.zMax = zMax;
	}

	public float getYMax() {
		return this.yMax;
	}

	public void setYMax(float yMax) {
		this.yMax = yMax;
	}

	public float getXMax() {
		return this.xMax;
	}

	public void setXMax(float xMax) {
		this.xMax = xMax;
	}
}
