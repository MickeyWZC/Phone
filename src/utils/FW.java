package utils;

public class FW {
	public static boolean circle(int x0,int y0,int r,int x,int y){
		return (x0-x)*(x0-x)+(y0-y)*(y0-y)<r*r;
	}
	public static boolean rec(int x0,int y0,int width,int height,int x,int y){
		return (x>x0&&x<x0+width&&y>y0&&y<y0+height);
	}
}
