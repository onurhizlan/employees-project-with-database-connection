
package JDBCandMYSQL.CalisanlarProjesi;

import JDBCandMYSQL.Baglanti;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CalisanIslemleri {
    
         private Connection con = null;
         private Statement statement = null;
         private PreparedStatement preparedStatement = null;
    
    public CalisanIslemleri(){ //constructorımız
          String url = "jdbc:mysql://" + Database.host + ":" + Database.port + "/" + Database.db_isim + "?useUnicode=true&characterEncoding=utf8";
            try{
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Driver bulunamadı");
            }
            
            try{
                con = DriverManager.getConnection(url, Database.kullanici_adi, Database.parola);
                System.out.println("Baglantı başarılı");
            } catch (SQLException ex) {
                System.out.println("Baglantı başarısız");
              //  ex.printStackTrace();
            }
    }
    
    public boolean girisKontrol(String kullaniciAdi, String sifre){
            String sorgu = "Select * From adminler where username = ? and password = ? ";
             try {
                 preparedStatement = con.prepareStatement(sorgu);
                 preparedStatement.setString(1, kullaniciAdi);
                 preparedStatement.setString(2, sifre);
                 ResultSet rs = preparedStatement.executeQuery();
                 if (rs.next() ==true) {
                      return true;
                 }else{
                     return false;
                 }
                 //yada return rs.next(); yapabiliriz if yerine
             } catch (SQLException ex) {
                 Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
                 return false;
             }
    }       
    public ArrayList<Calisan> calisanlariGetir(){
           ArrayList<Calisan> cikti = new ArrayList<Calisan>();
             try {
                 statement = con.createStatement();
                 String sorgu = "Select * From calisanlar";
                 ResultSet rs = statement.executeQuery(sorgu);
                 while(rs.next()){
                     int id = rs.getInt("id");
                     String ad = rs.getString("ad");
                     String soyad = rs.getString("soyad");
                     String depatman = rs.getString("departman");
                     String maas = rs.getString("maas");
                     
                     cikti.add(new Calisan(id, ad, soyad, depatman, maas));
                 }
                    return cikti;
             } catch (SQLException ex) {
                 Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
                 return null;
             }
    }
    
    
    public void calisanEkle(String ad, String soyad, String departman, String maas){
        String sorgu = "Insert Into calisanlar (ad, soyad, departman, maas) Values (?,?,?,?)";
        
            try{
             preparedStatement = con.prepareStatement(sorgu);
             preparedStatement.setString(1, ad);
             preparedStatement.setString(2, soyad);
             preparedStatement.setString(3, departman);
             preparedStatement.setString(4, maas);
             preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    public void calisanGuncelle(int id, String ad, String soyad, String departman, String maas){
            String sorgu = "Update calisanlar set ad = ? , soyad = ? , departman = ? , maas = ? where id = ?";
             try {
                 preparedStatement = con.prepareStatement(sorgu);
                 preparedStatement.setString(1, ad);
                 preparedStatement.setString(2, soyad);
                 preparedStatement.setString(3, departman);
                 preparedStatement.setString(4, maas);
                 preparedStatement.setInt(5, id);
                 preparedStatement.executeUpdate();
             } catch (SQLException ex) {
                 Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
             }       
    }
    public void calisanSil(int id){
            String sorgu = "Delete From calisanlar where id = ? ";
             try {
                 preparedStatement = con.prepareStatement(sorgu);
                 preparedStatement.setInt(1, id);
                 preparedStatement.executeUpdate();
             } catch (SQLException ex) {
                 Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
    
    public static void main(String[] args) {
            CalisanIslemleri baglanti = new CalisanIslemleri();
            
    }
}
