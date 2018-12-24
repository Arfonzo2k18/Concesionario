package com.example.alfon.concesionario.Clases;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class GenerarPDF {

    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitulo = new Font(Font.FontFamily.HELVETICA, 60, Font.BOLD);
    private Font fSubTitulo = new Font(Font.FontFamily.HELVETICA, 40, Font.BOLD);
    private Font fFecha = new Font(Font.FontFamily.HELVETICA, 20, Font.ITALIC);
    private Font fText = new Font(Font.FontFamily.HELVETICA, 20, Font.NORMAL);
    private Font fHeadTable = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, BaseColor.WHITE);
    private Font fPieTable = new Font(Font.FontFamily.HELVETICA, 25, Font.ITALIC, BaseColor.WHITE);


    public GenerarPDF(Context context) {
        this.context = context;
    }

    /**
     * Crea la carpeta contenedora y el archivo PDF en el que se trabajará
     */
    private void createFile(){
        File carpeta = new File(Environment.getExternalStorageDirectory().toString(), "PDF");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
        this.pdfFile = new File(carpeta, "Presupuesto Concesionario.pdf");
    }

    /**
     * Abre el documento como escritura para poder modificarlo
     */
    public void openDocument(){
        this.createFile();
        try {
            this.document = new Document();
            this.pdfWriter = PdfWriter.getInstance(this.document, new FileOutputStream(this.pdfFile));
            this.document.addCreationDate();
            this.document.addProducer();
            this.document.setPageSize(PageSize.A4);
            this.document.open();
        }catch (Exception e){
            Log.e("openDocument", e.toString());
        }
    }

    /**
     * Cierra el documento
     */
    public void closeDocument(){
        this.document.close();
        this.pdfWriter.close();
    }

    /**
     * Añade los metadatos del PDF
     *
     * @param titulo :String
     * @param tema :String
     * @param autor :String
     */
    public void addMetaData(String titulo, String tema, String autor){
        this.document.addTitle(titulo);
        this.document.addSubject(tema);
        this.document.addAuthor(autor);
    }

    /**
     * Añade una cabecera al PDF
     *
     * @param titulo :String
     * @param subtitulo :String
     */
    public void addTtitulo(String titulo, String subtitulo){
        try {
            this.paragraph = new Paragraph();
            this.addParagraph(new Paragraph(titulo, fTitulo));
            this.addParagraph(new Paragraph(subtitulo, fSubTitulo));
            this.addParagraph(new Paragraph("Generado: " + new Date().toString(), fFecha));
            this.paragraph.setSpacingAfter(30);
            this.document.add(this.paragraph);
        }catch (Exception e){
            Log.e("addTtitulo", e.toString());
        }
    }

    /**
     *
     * @param paragraphHijo
     */
    private void addParagraph(Paragraph paragraphHijo){
        paragraphHijo.setAlignment(Element.ALIGN_CENTER);
        this.paragraph.add(paragraphHijo);
    }

    /**
     *
     * @param paragraphHijo
     */
    private void addParagraphCliente(Paragraph paragraphHijo){
        paragraphHijo.setAlignment(Element.ALIGN_LEFT);
        this.paragraph.add(paragraphHijo);
    }

    public void addDatosCliente(String nombre, String apellidos, String email, int telefono, String poblacion, String direccion){
        try {
            this.paragraph = new Paragraph();
            this.addParagraphCliente(new Paragraph("Cliente: " ));
            this.addParagraphCliente(new Paragraph("Nombre: " + nombre));
            this.addParagraphCliente(new Paragraph("Apellidos: " + apellidos));
            this.addParagraphCliente(new Paragraph("Email: " + email));
            this.addParagraphCliente(new Paragraph("Telefono: " + telefono));
            this.addParagraphCliente(new Paragraph("Población: " + poblacion));
            this.addParagraphCliente(new Paragraph("Dirección: " + direccion));

            this.paragraph.setSpacingBefore(5);
            this.paragraph.setSpacingAfter(5);
            this.document.add(this.paragraph);
        }catch (Exception e){
            Log.e("addDatosCliente", e.toString());
        }
    }

    /**
     * Crea la tabla de presupuesto
     *
     * @param numColumnas : Total de columnas del presupuesto
     */
  public void crearTablaPresupuesto(int numColumnas, String nombre_coche, String precio_coche, ArrayList<Extra> array){
        try {
            this.paragraph = new Paragraph();
            this.paragraph.setFont(fText);
             //Se crea la tabla
            PdfPTable pdfPTable = new PdfPTable(numColumnas);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell; //La celda donde se escribirá

             //CABECERA
            int headIndex = 0;
            while (headIndex < numColumnas) {
                pdfPCell = new PdfPCell(new Phrase(nombre_coche + " " + precio_coche + "€", fHeadTable));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setPadding(10);
                pdfPCell.setBackgroundColor(new BaseColor(0, 0, 0));
                pdfPTable.addCell(pdfPCell);
                headIndex++;
            }

            //CUERPO
            for (int i = 0; i < numColumnas; i++){
                pdfPCell = new PdfPCell(new Phrase(array.get(i).getNombre_extra(), fText));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setPadding(10);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(String.valueOf(array.get(i).getPrecio_extra()) + "€", fText));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setPadding(10);
                pdfPTable.addCell(pdfPCell);
            }

           // PIE
            pdfPCell = new PdfPCell(new Phrase("Total", fPieTable));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBackgroundColor(new BaseColor(0, 0, 0));
            pdfPCell.setPadding(10);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Phrase(precio_coche, fPieTable));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBackgroundColor(new BaseColor(0, 0, 0));
            pdfPCell.setPadding(10);
            pdfPTable.addCell(pdfPCell);

            this.paragraph.setSpacingBefore(10);
            this.paragraph.setSpacingAfter(10);
            this.paragraph.add(pdfPTable);
            this.document.add(this.paragraph);
        }catch (Exception e){
            Log.e("crearTablaPresupuesto", e.toString());
        }
    }

    /**
     * Muestra la ruta absoluta del archivo PDF
     *
     * @return :String
     */
    public String verPathPDF(){
        return this.pdfFile.getAbsolutePath();
    }
}
