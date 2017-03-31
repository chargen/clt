package snu.crypto.lll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Matrix<V extends Number>
{

   int rowSize;
   int colSize;
   Number [][] values;

   private void allocMatrix() {
      values = new Number[rowSize][];
      for (int i=0; i<values.length; i++) {
         values[i] = new Number[colSize];
      }
   }
   
   public Matrix(int rowSize, int colSize) {
      this.rowSize = rowSize;
      this.colSize = colSize;
      allocMatrix();
   }
   
   public Matrix() {
      rowSize = -1;
      colSize = -1;
      values = null;
   }
   
   public int getNumberOfRows() {
      return rowSize;
   }
   
   public int getNumberOfColumns() {
      return colSize;
   }
   
   public V get(int row,int col) {
      return (V)values[row][col];
   }
   
   public void set(int row,int col, V value) {
      values[row][col] = value;
   }
   
   public Number [] getRow(int row) {
      return values[row];
   }
   
   public void read(Reader input,NumberConstructor<V> nconst) 
      throws java.io.IOException 
   {
      read(input,nconst,false,null);
   }
   
   public void read(Reader input,NumberConstructor<V> nconst,boolean reverse,int [] reorder) 
      throws java.io.IOException 
   {
      Logger log = Logger.getAnonymousLogger();
      boolean isFineLog = log.isLoggable(Level.FINE);
      BufferedReader breader = new BufferedReader(input);
      String spec = breader.readLine();
      if (isFineLog) {
         log.fine("Matrix spec line: "+spec);
      }
      if (spec==null) {
         throw new java.io.IOException("EOF before spec line was read.");
      }
      String [] svalues = spec.split("\\s");
      if (svalues.length>2) {
         throw new java.io.IOException("Row/Column spec line has too many values (length="+svalues.length+")");
      }
      rowSize = Integer.parseInt(svalues[0]);
      colSize = Integer.parseInt(svalues[1]);
      allocMatrix();
      
      if (reverse) {
         reorder = new int[colSize];
         for (int i=0; i<reorder.length; i++) {
            reorder[i] = reorder.length-i-1;
         }
      }
      
      for (int i=0; i<rowSize; i++) {
         String line = breader.readLine();
         if (isFineLog) {
            log.fine("Matrix input: "+line);
         }
         svalues = line.trim().split("\\s+");
         if (svalues.length!=colSize) {
            throw new java.io.IOException("Bad data line at "+(i+2)+", length is "+svalues.length);
         }
         for (int j=0; j<svalues.length; j++) {
            V exp = nconst.parse(svalues[j]);
            int index = reorder==null ? j : reorder[j];
            values[i][index] = exp;
         }
      }
      
   }
   
   public void write(OutputStream os)
      throws IOException
   {
      Writer out = new OutputStreamWriter(os);
      write(out);
      out.flush();
   }
   
   public void write(Writer out) 
      throws IOException
   {
      out.write(values.length+" "+values[0].length);
      out.write('\n');
      for (int row=0; row<values.length; row++) {
         for (int col=0; col<values[row].length; col++) {
            out.write(values[row][col].toString());
            out.write(' ');
         }
         out.write('\n');
      }
      out.flush();
   }
   
}