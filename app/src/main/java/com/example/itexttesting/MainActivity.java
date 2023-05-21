package com.example.itexttesting;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;

import com.itextpdf.kernel.colors.ColorConstants;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.GrooveBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);
        textView.setPaintFlags(textView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        textView.setOnClickListener(v -> {
            Toast.makeText(this,"click",Toast.LENGTH_LONG).show();
            try{
                createPDF();
            } catch (FileNotFoundException e) {
                e.getStackTrace();
            }


        });



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //@SuppressLint("NewApi")
    private void createPDF() throws  FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

        File file = new File(pdfPath,"my02PDF.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        //simple paragraph line in pdf
        Paragraph paragraph = new Paragraph("this a pdf.");
        document.add(paragraph);

        //simple paragraph line in pdf but different style
        Text text1 = new Text("Bold").setBold();
        Text text2 = new Text("Italic").setItalic();
        Text text3 = new Text("Underline").setUnderline();
        Paragraph paragraph1 = new Paragraph("the style of word.\n");
        paragraph1.add(text1)
                        .add(text2)
                                .add(text3);
        document.add(paragraph1);

        //simple paragraph line in pdf but in list view
        List list = new List();
        list.add("Android");
        list.add("Java");
        list.add("C Programming");
        list.add("C++");
        list.add("C#");
        list.add("Kotlin");
        Paragraph paragraph2 = new Paragraph("the simple list.\n");
        document.add(paragraph2);
        document.add(list);

        List list1 = new List();
        list1.setListSymbol("\u00A5");
        list1.add("Apple");
        list1.add("Orange");
        list1.add("Mango");
        list1.add("Banana");
        list1.add("Cherry");
        list1.add("Watermelon");
        list1.add("Greps");
        Paragraph paragraph21 = new Paragraph("the simple Fruit list with add Symbol.\n");
        document.add(paragraph21);
        document.add(list1);


        Drawable d = getDrawable(R.drawable.fruit);
        Bitmap bitmap1 = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG,100,outputStream1);
        byte[] bitmapData1 = outputStream1.toByteArray();
        ImageData imageData1 = ImageDataFactory.create(bitmapData1);
        Image img = new Image(imageData1);
        img.setWidth(16);
        img.setHeight(16);
        List list2 = new List();
        list2.setListSymbol(img);
        list2.add("Apple");
        list2.add("Orange");
        list2.add("Mango");
        list2.add("Banana");
        list2.add("Cherry");
        list2.add("Watermelon");
        list2.add("Greps");
        Paragraph paragraph22 = new Paragraph("the simple Fruit list with add image.\n");
        document.add(paragraph22);
        document.add(list2);

        //image add in pdf
       Drawable drawable = getDrawable(R.drawable.fruit);
       Bitmap bitmap = ((BitmapDrawable)drawable ).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bitmapData = stream.toByteArray();
        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);
        image.setHeight(100);
        image.setWidth(100);
        Paragraph paragraph3 = new Paragraph("\u058D the image add:");
        document.add(paragraph3);
        document.add(image);

        //the Border use table
        Border border = new GrooveBorder(2);

        //table form entry add in pdf and color also
        float columnWidth[] = {200f,200f};
        Table table = new Table(columnWidth);
        table.addCell("Name");
        table.addCell("Age");

        table.addCell(new Cell().setBackgroundColor(ColorConstants.GREEN).add(new Paragraph("Raja Ram")));
        table.addCell("32");
        table.addCell(new Cell().setBackgroundColor(new DeviceRgb(244,23,213)).setBorder(border).add(new Paragraph("Raja Babu")));
        table.addCell("25");
        table.addCell("Summit");
        table.addCell("21");
        //new thing two row in one main row i.e. rows pan and cols pan
        table.addCell(new Cell(2,1).add(new Paragraph("Rajesh-war Bambara")));
        table.addCell("32");
        table.addCell("62");
        table.setBorder(border);
        Paragraph paragraph4 = new Paragraph("the table add in pdf.\n");
        document.add(paragraph4);
        document.add(table);

        //ADD Barcode or QRCode in pdf
        BarcodeQRCode qrCode = new BarcodeQRCode("Hello World!");//mailto:vaibhavgite600@gmail.com?subject=Sarthi%20Technology&body=Hello Friends this is Sarthi Technology
        PdfFormXObject barcodeObject = qrCode.createFormXObject(ColorConstants.BLACK,pdfDocument);
        Image barCodeImage = new Image(barcodeObject).setWidth(100f).setHeight(100f);
        Paragraph paragraph5 = new Paragraph("the Barcode add in pdf.\n");
        document.add(paragraph5);
        document.add(barCodeImage);

        //



        document.close();
        Toast.makeText(this,"pdf Created",Toast.LENGTH_LONG).show();

    }
}