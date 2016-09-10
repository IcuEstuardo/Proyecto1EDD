package edicionimagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Isabel
 */
public class Imagen {

    private BufferedImage ImagenE;
    private int Ancho, Alto, Diagonal;
    private int Xc, Yc, tmPx, tmPy;

    public void abrirImagen() {

        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccione una imagen");
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG & GIF & BMP", "jpg", "gif", "bmp");
        selector.setFileFilter(filtroImagen);
        int flag = selector.showOpenDialog(null);

        if (flag == JFileChooser.APPROVE_OPTION) {
            try {
                File imagenSeleccionada = selector.getSelectedFile();
                this.setImagenE(ImageIO.read(imagenSeleccionada));
            } catch (Exception e) {
            }
        }
    }

    public void DatosPixeles(int tam) {
        this.Ancho = this.getImagenE().getWidth();
        this.Alto = this.getImagenE().getHeight();
        this.Diagonal = (int) Math.sqrt((Math.pow(this.Ancho, 2) + Math.pow(this.Alto, 2)));

        this.Xc = (tam * this.Ancho) / this.Diagonal;
        this.Yc = (tam * this.Alto) / this.Diagonal;

        this.tmPx = this.Ancho / this.Xc;
        this.tmPy = this.Alto / this.Yc;

        System.out.println("Ancho: " + this.Ancho + "   Alto: " + this.Alto + "   Hipotenusa: " + this.Diagonal);
        System.out.println("Valor Xc: " + this.Xc + "   Valor Yc: " + this.Yc);
        System.out.println("Tamaño Pixel X: " + this.tmPx + "   Tamaño Pixel Y: " + this.tmPy);
    }
    
    private void ColorearImagen(int Xini, int Yini, BufferedImage img) {
        int sumR = 0, sumG = 0, sumB = 0;
        
        if (Xini + tmPx < Ancho && Yini + tmPy < Alto) {
            for (int y = Yini; y < (Yini + tmPy); y++) {
                for (int x = Xini; x < (Xini + tmPx); x++) {
                    Color color = new Color(img.getRGB(x, y));
                    sumR += color.getRed();
                    sumG += color.getGreen();
                    sumB += color.getBlue();
                }
            }
            
            int promR = sumR/(tmPy * tmPx);
            int promG = sumG/(tmPy * tmPx);
            int promB = sumB/(tmPy * tmPx);
            
            for (int y = Yini; y < (Yini + tmPy); y++) {
                for (int x = Xini; x < (Xini + tmPx); x++) {
                    Color color = new Color(promR, promG, promB);
                    img.setRGB(x, y, color.getRGB());
                }
            }
        }
        
        
        
        
           
    }

    public BufferedImage PixelarImagen() {
        BufferedImage salida = this.getImagenE();
        
        for (int y = 0; y < this.Alto; y+=tmPy) {
            for (int x = 0; x < this.Ancho; x+=tmPx) {
                this.ColorearImagen(x, y, salida);
            }
        }

        return salida;
    }

    public BufferedImage getImagenE() {
        return ImagenE;
    }

    public void setImagenE(BufferedImage ImagenE) {
        this.ImagenE = ImagenE;
    }
    
}
