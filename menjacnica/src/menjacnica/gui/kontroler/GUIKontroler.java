package menjacnica.gui.kontroler;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.DodajKursGUI;
import menjacnica.gui.IzvrsiZamenuGUI;
import menjacnica.gui.MenjacnicaGUI;
import menjacnica.gui.ObrisiKursGUI;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {	
	public static MenjacnicaInterface sistem = new Menjacnica();

	public static MenjacnicaGUI gp;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenjacnicaGUI frame = new MenjacnicaGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gp, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				gp.prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gp, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(gp);
		prozor.setVisible(true);
	}
	
	public static void prikaziObrisiKursGUI() {
		
		if (MenjacnicaGUI.table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(MenjacnicaGUI.table.getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(gp,
					model.vratiValutu(MenjacnicaGUI.table.getSelectedRow()));
			prozor.setLocationRelativeTo(gp);
			prozor.setVisible(true);
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI() {
		if (MenjacnicaGUI.table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(MenjacnicaGUI.table.getModel());
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(gp,
					model.vratiValutu(MenjacnicaGUI.table.getSelectedRow()));
			prozor.setLocationRelativeTo(gp);
			prozor.setVisible(true);
		}
	}
	
	public static void unesiKurs(String naziv, String skraceniNaziv, int sifra, double prodajniKurs, double kupovniKurs, double srednjiKurs) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajniKurs);
			valuta.setKupovni(kupovniKurs);
			valuta.setSrednji(srednjiKurs);
			
			// Dodavanje valute u kursnu listu
			sistem.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			gp.prikaziSveValute();
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void izvrsiZamenu(Valuta valuta, boolean prodaja, double iznos){
		try{
			double konacniIznos = 
					sistem.izvrsiTransakciju(valuta, prodaja, iznos);
		
			IzvrsiZamenuGUI.textFieldKonacniIznos.setText(""+konacniIznos);
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(gp, e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
	}
	}
	
	public static void obrisiValutu(Valuta valuta) {
		try{
			sistem.obrisiValutu(valuta);
			
			gp.prikaziSveValute();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gp, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
