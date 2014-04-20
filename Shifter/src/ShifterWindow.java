import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShifterWindow extends JFrame
{
	private static final long serialVersionUID = -2781946293630895800L;
	private Shifter game;
	private final static byte[] bcatch = { -119, 80, 78, 71, 13, 10, 26, 10, 0,
			0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 32, 0, 0, 0, 32, 8, 2, 0, 0, 0,
			-4, 24, -19, -93, 0, 0, 0, 84, 73, 68, 65, 84, 120, -38, 99, -88,
			-104, -69, -99, -90, -120, 97, -44, -126, 17, 105, -63, 1, 114,
			-63, -126, 5, 11, 104, 104, -63, 2, 48, 32, -63, -126, 5, 100, 1,
			90, 89, 64, -66, 15, -38, 54, -100, 33, -120, 40, 10, -94, 81, 11,
			70, 45, 24, -75, 96, -44, 2, 34, 44, -96, 109, 113, 77, -61, -6,
			96, 1, -71, -128, 40, 11, 22, 80, 0, 70, -101, 45, -93, 22, -48, 2,
			1, 0, 10, 109, -111, 74, 16, -103, -14, 126, 0, 0, 0, 0, 73, 69,
			78, 68, -82, 66, 96, -126 };
	private final static byte[] bdown = { -119, 80, 78, 71, 13, 10, 26, 10, 0,
			0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 32, 0, 0, 0, 32, 8, 2, 0, 0, 0,
			-4, 24, -19, -93, 0, 0, 0, 114, 73, 68, 65, 84, 120, -38, -19,
			-107, -79, 13, -64, 48, 8, 4, 25, -37, -53, 100, -114, -76, 30, 45,
			109, 20, 69, -4, -127, 21, -89, 121, -53, -27, 115, -25, 2, 112,
			-116, -29, -4, -12, -122, 5, 22, 88, -16, -117, 96, -34, 14, 65,
			-28, 121, 33, -112, 14, 25, -42, -126, -60, 65, -110, 72, -16, 90,
			9, 99, 1, -97, -10, 40, -122, -12, -84, -117, 18, 4, -89, -117, 54,
			-99, -8, -12, -25, 96, -111, -114, 6, 109, -123, 78, 39, -71, 77,
			47, -84, -118, 30, -67, -74, -117, 26, -12, -14, -78, -85, -46, -3,
			31, 88, 96, -63, 30, -63, 5, -73, -21, 106, 106, 54, -118, 73, 112,
			0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126 };
	private final static byte[] bicon = { -119, 80, 78, 71, 13, 10, 26, 10, 0,
			0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 32, 0, 0, 0, 32, 8, 2, 0, 0, 0,
			-4, 24, -19, -93, 0, 0, 0, 62, 73, 68, 65, 84, 120, -38, 99, -88,
			-104, -69, -99, -90, -120, 97, -44, -126, 17, 105, -63, 1, 36, -80,
			0, 9, 16, 35, 62, 106, 1, 117, 44, 32, -43, 80, 100, -15, 81, 11,
			-88, 99, -63, 104, 50, 29, -51, 7, -93, 22, -116, 38, -45, -63, 96,
			-63, 104, -77, 101, -44, 2, 18, 17, 0, 123, 21, -99, -86, -102,
			-82, -118, -9, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126 };
	private final static byte[] bright = { -119, 80, 78, 71, 13, 10, 26, 10, 0,
			0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 32, 0, 0, 0, 32, 8, 2, 0, 0, 0,
			-4, 24, -19, -93, 0, 0, 0, 104, 73, 68, 65, 84, 120, -38, 99, -88,
			-104, -69, -99, -90, -120, 97, -44, -126, 17, 111, -63, 1, 36, 64,
			115, 11, -56, -77, -125, 52, 11, -56, -80, -125, 100, 11, 72, -75,
			-125, 112, 36, 83, 104, 7, 81, -87, -120, 18, 59, -120, 77, -90,
			100, -37, 65, 66, 62, 32, -49, 14, -46, 50, 26, 25, 118, -112,
			-100, -109, 73, -75, 99, -112, 89, 64, -37, 32, -94, 109, 36, -45,
			54, -103, -46, 54, -93, -47, -74, -88, -96, 121, 97, 71, -17, -30,
			122, 8, 86, -103, -93, -51, -106, 81, 11, -120, 64, 0, -69, 84,
			106, 106, -83, -24, -17, 96, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66,
			96, -126 };
	private final static byte[] bleft = { -119, 80, 78, 71, 13, 10, 26, 10, 0,
			0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 32, 0, 0, 0, 32, 8, 2, 0, 0, 0,
			-4, 24, -19, -93, 0, 0, 0, 106, 73, 68, 65, 84, 120, -38, -19,
			-106, 49, 14, 0, 32, 8, 3, 121, -74, -97, -15, 29, -82, 60, -51,
			85, -94, 70, 4, -84, 11, 77, -25, 94, -94, 82, -92, 82, -37, 83,
			83, 2, 18, -80, 49, 15, -118, 7, -80, 84, 48, -128, 39, 69, 2, 120,
			-91, 48, -128, 33, -3, 2, 96, 75, -41, 2, -52, -23, 42, -128, 39,
			-3, 12, 112, -90, 31, 0, -2, -12, -33, -128, -25, 71, -124, -72,
			100, -60, 51, 69, 12, 26, -94, 42, 16, 101, -121, -88, 107, -60,
			-62, 65, -84, -52, -4, -74, 36, 64, -70, 3, 40, -124, 106, 106,
			-117, 1, 48, -128, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126 };
	private final static byte[] bp1 = { -119, 80, 78, 71, 13, 10, 26, 10, 0, 0,
			0, 13, 73, 72, 68, 82, 0, 0, 0, 32, 0, 0, 0, 32, 8, 2, 0, 0, 0, -4,
			24, -19, -93, 0, 0, 0, 84, 73, 68, 65, 84, 120, -38, 99, -88, -104,
			-69, -99, -90, -120, 97, -44, -126, 81, 11, 70, 45, -96, -65, 5, 7,
			112, 3, 60, -90, -32, 81, 64, -126, 5, -72, -116, -64, 47, -53, 64,
			-92, 91, -16, -117, -45, -48, 2, 50, -125, -120, -26, 22, -112, 26,
			-49, -44, -79, -128, -102, -87, -120, -44, -60, 62, 106, 1, 65,
			-93, -15, 68, 21, -35, 45, 24, 45, -82, 71, 45, 24, -75, 96, 40,
			90, 0, 0, 3, -32, 91, 118, 5, 9, -55, 94, 0, 0, 0, 0, 73, 69, 78,
			68, -82, 66, 96, -126 };
	private final static byte[] bp2 = { -119, 80, 78, 71, 13, 10, 26, 10, 0, 0,
			0, 13, 73, 72, 68, 82, 0, 0, 0, 32, 0, 0, 0, 32, 8, 2, 0, 0, 0, -4,
			24, -19, -93, 0, 0, 0, 98, 73, 68, 65, 84, 120, -38, 99, -88, -104,
			-69, -99, -90, -120, 97, -44, -126, 81, 11, 70, 45, -96, -65, 5, 7,
			112, 3, -126, 106, 40, -75, 0, 110, 4, 49, -114, 32, 96, 1, -90,
			58, 60, 110, 68, -77, -107, 86, 22, -32, 82, 51, 64, 22, -112, 20,
			-115, -28, 4, 17, -15, -90, 83, 45, -110, 9, 26, 77, 90, 50, 37,
			-55, 116, 114, 50, 26, -111, -90, -109, -97, -109, 73, 13, 28, 60,
			30, -94, -69, 5, -93, -59, -11, -88, 5, -93, 22, 12, 69, 11, 0,
			-10, 25, 105, 14, -54, 7, 15, 5, 0, 0, 0, 0, 73, 69, 78, 68, -82,
			66, 96, -126 };
	private final static byte[] btp = { -119, 80, 78, 71, 13, 10, 26, 10, 0, 0,
			0, 13, 73, 72, 68, 82, 0, 0, 0, 32, 0, 0, 0, 32, 8, 2, 0, 0, 0, -4,
			24, -19, -93, 0, 0, 0, 71, 73, 68, 65, 84, 120, -38, 99, -88, -104,
			-69, -99, -90, -120, 97, -44, -126, 81, 11, 70, 45, -96, -65, 5, 7,
			-16, 2, -126, 106, 104, 110, 1, -90, 29, -8, -126, 8, -105, -93,
			72, 18, 31, -55, 22, -48, 60, 14, -56, 76, 69, -108, 4, 17, -55,
			25, 109, -44, -126, -127, -73, 96, -76, -72, 30, -75, 96, -44,
			-126, -95, 98, 1, 0, 76, -68, 93, -70, -114, -4, -27, 17, 0, 0, 0,
			0, 73, 69, 78, 68, -82, 66, 96, -126 };
	private final static byte[] btn = { -119, 80, 78, 71, 13, 10, 26, 10, 0, 0,
			0, 13, 73, 72, 68, 82, 0, 0, 0, 32, 0, 0, 0, 32, 8, 2, 0, 0, 0, -4,
			24, -19, -93, 0, 0, 0, 94, 73, 68, 65, 84, 120, -38, 99, -88, -104,
			-69, -99, -90, -120, 97, -44, -126, 81, 11, 70, 45, -96, -65, 5, 7,
			-16, 2, 100, 53, -104, 102, 97, 21, -89, -69, 5, 4, 53, 32, 59,
			-126, -26, 22, -96, -55, -46, -60, 2, 100, 5, 84, -74, 0, 51, -84,
			-88, 111, 1, -102, 29, 52, -79, 0, 79, -120, 81, -51, 2, 60, -47,
			78, 53, 11, -16, -92, 93, -86, 89, 64, -61, 56, 32, -33, -126, -47,
			-30, 122, -44, -126, 81, 11, -122, -118, 5, 0, -30, 98, 96, 114,
			97, -90, 39, 41, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126 };
	private final static byte[] bup = { -119, 80, 78, 71, 13, 10, 26, 10, 0, 0,
			0, 13, 73, 72, 68, 82, 0, 0, 0, 32, 0, 0, 0, 32, 8, 2, 0, 0, 0, -4,
			24, -19, -93, 0, 0, 0, 99, 73, 68, 65, 84, 120, -38, -19, -43, 49,
			10, 0, 33, 12, 68, -47, 57, -10, 94, -58, 115, -40, -50, -47, 22,
			-84, 4, 89, 113, -110, -40, 44, -13, -79, -108, -68, 38, 40, -98,
			-42, -81, 30, 24, 48, 96, 64, 4, 56, -70, 5, 112, -86, 30, -32, 82,
			37, -64, -113, 106, 0, 110, -53, 2, 60, 40, 14, -16, -72, 8, -80,
			25, 36, 25, 80, -89, -85, 6, 2, -45, -91, 107, -56, -84, 124, 13,
			32, -83, -103, 12, -28, -97, 16, -1, 7, 6, 12, -4, 1, 120, 1, 43,
			-19, 106, 106, 78, -38, 48, -69, 0, 0, 0, 0, 73, 69, 78, 68, -82,
			66, 96, -126 };
	private final static byte[] btile = { -119, 80, 78, 71, 13, 10, 26, 10, 0,
			0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 32, 0, 0, 0, 32, 8, 2, 0, 0, 0,
			-4, 24, -19, -93, 0, 0, 0, 70, 73, 68, 65, 84, 120, -38, 99, 56,
			64, 75, -80, 96, -63, 2, 6, -38, 25, 13, 1, 8, 11, -38, 54, -100,
			-95, 34, 66, -73, -128, -70, -90, 35, -37, 49, 106, -63, -88, 5,
			-93, 22, -116, 90, 48, 106, -63, -88, 5, -93, 22, -116, 90, 48,
			106, 1, 29, 45, -128, -80, 104, -40, 116, 92, 64, 75, -64, -80,
			-128, -58, 0, 0, -12, -21, 19, -15, 84, -25, 74, -47, 0, 0, 0, 0,
			73, 69, 78, 68, -82, 66, 96, -126 };
	private final static byte[] bspace = { -119, 80, 78, 71, 13, 10, 26, 10, 0,
			0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 32, 0, 0, 0, 32, 8, 2, 0, 0, 0,
			-4, 24, -19, -93, 0, 0, 0, 42, 73, 68, 65, 84, 120, -38, -19, -51,
			65, 9, 0, 0, 8, 4, -80, -117, 109, 25, 115, 88, -49, 20, 62, -124,
			-63, -2, 75, -11, -100, -118, 64, 32, 16, 8, 4, 2, -127, 64, 32,
			-8, 18, 44, -99, -52, 48, 106, -31, 33, -128, -110, 0, 0, 0, 0, 73,
			69, 78, 68, -82, 66, 96, -126 };
	private BufferedImage boardspace;
	private BufferedImage boardup;
	private BufferedImage boarddown;
	private BufferedImage boardleft;
	private BufferedImage boardright;
	private BufferedImage boardp1;
	private BufferedImage boardp2;
	private BufferedImage boardtn;
	private BufferedImage boardtile;
	private BufferedImage boardtp;
	private BufferedImage boardcatcher;
	private BufferedImage boardicon;
	public static final int TILE = 32;

	public ShifterWindow()
	{
		game = new Shifter();
		InputStream in = new ByteArrayInputStream(btile);
		try {
			boardtile = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in = new ByteArrayInputStream(bicon);
		try {
			boardicon = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in = new ByteArrayInputStream(bup);
		try {
			boardup = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in = new ByteArrayInputStream(bdown);
		try {
			boarddown = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in = new ByteArrayInputStream(bleft);
		try {
			boardleft = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in = new ByteArrayInputStream(bright);
		try {
			boardright = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in = new ByteArrayInputStream(bp1);
		try {
			boardp1 = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in = new ByteArrayInputStream(bp2);
		try {
			boardp2 = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in = new ByteArrayInputStream(btn);
		try {
			boardtn = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in = new ByteArrayInputStream(btp);
		try {
			boardtp = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in = new ByteArrayInputStream(bspace);
		try {
			boardspace = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in = new ByteArrayInputStream(bcatch);
		try {
			boardcatcher = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setContentPane(new JPanel()
		{
			private static final long serialVersionUID = -564389291878448003L;

			public void paint(Graphics g)
			{
				BufferedImage buf = new BufferedImage(8 * TILE, 9 * TILE,
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = (Graphics2D) buf.createGraphics();
				g2d.drawImage(boardicon, 0 * TILE, 0 * TILE, null);
				g2d.drawImage(boardleft, 1 * TILE, 0 * TILE, null);
				g2d.drawImage(boardspace, 2 * TILE, 0 * TILE, null);
				g2d.drawImage(boardspace, 3 * TILE, 0 * TILE, null);
				g2d.drawImage(boardspace, 4 * TILE, 0 * TILE, null);
				g2d.drawImage(boardright, 5 * TILE, 0 * TILE, null);
				g2d.drawImage(boardtp, 6 * TILE, 0 * TILE, null);
				g2d.drawImage(boardspace, 7 * TILE, 0 * TILE, null);

				g2d.drawImage(boardup, 0 * TILE, 1 * TILE, null);
				g2d.drawImage(boardtile, 1 * TILE, 1 * TILE, null);
				g2d.drawImage(boardtile, 2 * TILE, 1 * TILE, null);
				g2d.drawImage(boardtile, 3 * TILE, 1 * TILE, null);
				g2d.drawImage(boardtile, 4 * TILE, 1 * TILE, null);
				g2d.drawImage(boardtile, 5 * TILE, 1 * TILE, null);
				g2d.drawImage(boardspace, 6 * TILE, 1 * TILE, null);
				g2d.drawImage(boardup, 7 * TILE, 1 * TILE, null);

				g2d.drawImage(boardspace, 0 * TILE, 2 * TILE, null);
				g2d.drawImage(boardtile, 1 * TILE, 2 * TILE, null);
				g2d.drawImage(boardtile, 2 * TILE, 2 * TILE, null);
				g2d.drawImage(boardtile, 3 * TILE, 2 * TILE, null);
				g2d.drawImage(boardtile, 4 * TILE, 2 * TILE, null);
				g2d.drawImage(boardtile, 5 * TILE, 2 * TILE, null);
				g2d.drawImage(boardspace, 6 * TILE, 2 * TILE, null);
				g2d.drawImage(boardspace, 7 * TILE, 2 * TILE, null);

				g2d.drawImage(boardspace, 0 * TILE, 3 * TILE, null);
				g2d.drawImage(boardtile, 1 * TILE, 3 * TILE, null);
				g2d.drawImage(boardtile, 2 * TILE, 3 * TILE, null);
				g2d.drawImage(boardtile, 3 * TILE, 3 * TILE, null);
				g2d.drawImage(boardtile, 4 * TILE, 3 * TILE, null);
				g2d.drawImage(boardtile, 5 * TILE, 3 * TILE, null);
				g2d.drawImage(boardspace, 6 * TILE, 3 * TILE, null);
				g2d.drawImage(boardspace, 7 * TILE, 3 * TILE, null);

				g2d.drawImage(boardspace, 0 * TILE, 4 * TILE, null);
				g2d.drawImage(boardtile, 1 * TILE, 4 * TILE, null);
				g2d.drawImage(boardtile, 2 * TILE, 4 * TILE, null);
				g2d.drawImage(boardtile, 3 * TILE, 4 * TILE, null);
				g2d.drawImage(boardtile, 4 * TILE, 4 * TILE, null);
				g2d.drawImage(boardtile, 5 * TILE, 4 * TILE, null);
				g2d.drawImage(boardspace, 6 * TILE, 4 * TILE, null);
				g2d.drawImage(boardspace, 7 * TILE, 4 * TILE, null);

				g2d.drawImage(boarddown, 0 * TILE, 5 * TILE, null);
				g2d.drawImage(boardtile, 1 * TILE, 5 * TILE, null);
				g2d.drawImage(boardtile, 2 * TILE, 5 * TILE, null);
				g2d.drawImage(boardtile, 3 * TILE, 5 * TILE, null);
				g2d.drawImage(boardtile, 4 * TILE, 5 * TILE, null);
				g2d.drawImage(boardtile, 5 * TILE, 5 * TILE, null);
				g2d.drawImage(boardspace, 6 * TILE, 5 * TILE, null);
				g2d.drawImage(boarddown, 7 * TILE, 5 * TILE, null);

				g2d.drawImage(boardspace, 0 * TILE, 6 * TILE, null);
				g2d.drawImage(boardspace, 1 * TILE, 6 * TILE, null);
				g2d.drawImage(boardspace, 2 * TILE, 6 * TILE, null);
				g2d.drawImage(boardspace, 3 * TILE, 6 * TILE, null);
				g2d.drawImage(boardspace, 4 * TILE, 6 * TILE, null);
				g2d.drawImage(boardspace, 5 * TILE, 6 * TILE, null);
				g2d.drawImage(boardcatcher, 6 * TILE, 6 * TILE, null);
				g2d.drawImage(boardspace, 7 * TILE, 6 * TILE, null);

				g2d.drawImage(boardspace, 0 * TILE, 7 * TILE, null);
				g2d.drawImage(boardleft, 1 * TILE, 7 * TILE, null);
				g2d.drawImage(boardspace, 2 * TILE, 7 * TILE, null);
				g2d.drawImage(boardspace, 3 * TILE, 7 * TILE, null);
				g2d.drawImage(boardspace, 4 * TILE, 7 * TILE, null);
				g2d.drawImage(boardright, 5 * TILE, 7 * TILE, null);
				g2d.drawImage(boardspace, 6 * TILE, 7 * TILE, null);
				g2d.drawImage(boardspace, 7 * TILE, 7 * TILE, null);

				g2d.drawImage(boardp1, 0 * TILE, 8 * TILE, null);
				g2d.drawImage(boardspace, 1 * TILE, 8 * TILE, null);
				g2d.drawImage(boardspace, 2 * TILE, 8 * TILE, null);
				g2d.drawImage(boardtn, 3 * TILE, 8 * TILE, null);
				g2d.drawImage(boardspace, 4 * TILE, 8 * TILE, null);
				g2d.drawImage(boardp2, 5 * TILE, 8 * TILE, null);
				g2d.drawImage(boardspace, 6 * TILE, 8 * TILE, null);
				g2d.drawImage(boardspace, 7 * TILE, 8 * TILE, null);

				// catchers
				int[] d0 = game.getCatcher(0);
				int[] d1 = game.getCatcher(1);
				g2d.drawImage(boardcatcher, 6 * TILE, (1 + d0[0]) * TILE, null);
				g2d.drawImage(boardcatcher, (1 + d1[0]) * TILE, 6 * TILE, null);
				Point p0 = centreString(g2d, "" + d0[1], 6 * TILE + TILE / 2,
						(1 + d0[0]) * TILE + TILE / 2);
				Point p1 = centreString(g2d, "" + d1[1], (1 + d1[0]) * TILE
						+ TILE / 2, 6 * TILE + TILE / 2);
				g2d.drawString("" + d0[1], p0.x, p0.y);
				g2d.drawString("" + d1[1], p1.x, p1.y);

				// numbers
				int turn = game.getTurn();
				Point pt = centreString(g2d, "" + turn, 4 * TILE + TILE / 2, 8
						* TILE + TILE / 2);
				int tp = game.getTurnPoints();
				Point ptp = centreString(g2d, "" + tp, 7 * TILE + TILE / 2, 0
						* TILE + TILE / 2);
				int s0 = game.getScore(0);
				Point ps0 = centreString(g2d, "" + s0, 2 * TILE, 8 * TILE
						+ TILE / 2);
				int s1 = game.getScore(1);
				Point ps1 = centreString(g2d, "" + s1, 7 * TILE, 8 * TILE
						+ TILE / 2);
				g2d.drawString("" + turn, pt.x, pt.y);
				g2d.drawString("" + tp, ptp.x, ptp.y);
				g2d.drawString("" + s0, ps0.x, ps0.y);
				g2d.drawString("" + s1, ps1.x, ps1.y);

				// board numbers
				int[][] board = game.getBoard();
				for (int r = 0; r < 5; r++) {
					for (int c = 0; c < 5; c++) {
						Point ptile = centreString(g2d, "" + board[r][c],
								(1 + c) * TILE + TILE / 2, (1 + r) * TILE
										+ TILE / 2);
						g2d.drawString("" + board[r][c], ptile.x, ptile.y);
					}
				}
				g.drawImage(buf, 0, 0, null);
			}
		});
		Dimension dim = new Dimension(8 * TILE, 9 * TILE);
		getContentPane().setMaximumSize(dim);
		getContentPane().setMinimumSize(dim);
		getContentPane().setPreferredSize(dim);
		getContentPane().setSize(dim);

		getContentPane().addMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent evt)
			{
				int x = evt.getX() / TILE;
				int y = evt.getY() / TILE;
				if (x == 1 && y == 0)
					game.shifter(2);
				else if (x == 5 && y == 0)
					game.shifter(0);
				else if (x == 0 && y == 1)
					game.shifter(3);
				else if (x == 0 && y == 5)
					game.shifter(1);
				else if (x == 7 && y == 1)
					game.catcher(0, -1);
				else if (x == 7 && y == 5)
					game.catcher(0, 1);
				else if (x == 1 && y == 7)
					game.catcher(1, -1);
				else if (x == 5 && y == 7)
					game.catcher(1, 1);
				else if (x >= 1 && x <= 5 && y >= 1 && y <= 5)
					game.placer(y - 1, x - 1);
				else if (x >= 3 && x <= 4 && y == 8
						&& game.getTurnPoints() == 0) {
					if (game.nextTurn())
						setTitle("Shifter: Player "
								+ ((game.getTurn() - 1) % 2) + " wins!");
				} else if(x == 0 && y == 0 && game.isFinished())
				{
					setTitle("Shifter");
					game.newGame();
				}
				repaint();
			}

			public void mouseEntered(MouseEvent evt)
			{
			}

			public void mouseExited(MouseEvent evt)
			{
			}

			public void mousePressed(MouseEvent evt)
			{
			}

			public void mouseReleased(MouseEvent evt)
			{
			}
		});
		setResizable(false);
		pack();
		setTitle("Shifter");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public Point centreString(Graphics g, String str, int xi, int yi)
	{
		Rectangle2D r2d = g.getFontMetrics().getStringBounds(str, g);
		int xf = (int) (xi - r2d.getWidth() / 2);
		int yf = (int) (yi + r2d.getHeight() / 2);
		return new Point(xf, yf);
	}

	public static void main(final String[] args)
	{
		new ShifterWindow();
	}
}
