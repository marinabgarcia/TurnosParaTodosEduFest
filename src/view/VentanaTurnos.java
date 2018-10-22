package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class VentanaTurnos extends JFrame {

	private VentanaTurnos lamina;
	Calendar cal = new GregorianCalendar();
	DefaultTableModel model;
	DefaultTableModel modelCalendario;
	JLabel labelMes;

	public VentanaTurnos() {
		// setSize(600,300);
		// setLocation(400,100);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("Turnos para todos");

		JToolBar toolBar = getToolBar();

		getContentPane().add(toolBar, BorderLayout.NORTH);
		pack();

		// Agregamos la lamina
		JPanel lamina = new JPanel();
		lamina.setLayout(new BorderLayout(10, 20));
		add(lamina);

		// Armamos formulario
		JPanel laminaFormulario = new JPanel();
		laminaFormulario.setLayout(new GridLayout(0, 2));
		// agregamos la laminaFormulario
		add(laminaFormulario, BorderLayout.CENTER);

		// Profesional
		JPanel laminaIzquierdaMadre = new JPanel();
		laminaIzquierdaMadre.setLayout(new BorderLayout(30, 30));

		JPanel laminaIzquierda = new JPanel();
		laminaIzquierda.setLayout(new GridLayout(0, 1, 30, 30));
		laminaIzquierdaMadre.add(laminaIzquierda);

		JLabel titulo = new JLabel("Asignación de turnos", JLabel.CENTER);
		laminaIzquierdaMadre.add(titulo, BorderLayout.NORTH);

		// Calendario
		labelMes = new JLabel();
		labelMes.setHorizontalAlignment(SwingConstants.CENTER);

		JButton b1 = new JButton("<-");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				cal.add(Calendar.MONTH, -1);
				updateMonth();
			}
		});

		JButton b2 = new JButton("->");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				cal.add(Calendar.MONTH, +1);
				updateMonth();
			}
		});

		JPanel panelCalendario = new JPanel();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(b1, BorderLayout.WEST);
		panel.add(labelMes, BorderLayout.CENTER);
		panel.add(b2, BorderLayout.EAST);

		String[] columns = { "Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab" };
		modelCalendario = new DefaultTableModel(null, columns);
		JTable table = new JTable(modelCalendario);
		JScrollPane pane = new JScrollPane(table);

		panelCalendario.add(panel, BorderLayout.NORTH);
		panelCalendario.add(pane, BorderLayout.CENTER);

		laminaIzquierda.add(panelCalendario);

		this.updateMonth();

		// Profesional
		JPanel laminaProfesional = new JPanel();
		JLabel marcaLabel = new JLabel("Profesional", JLabel.LEFT);
		String[] marcas = { "", "Silvina Garcia", "Pablo Spoletti", "Rocío Perez" };
		JComboBox marca = new JComboBox(marcas);
		marca.setSelectedIndex(0);
		laminaProfesional.add(marcaLabel);
		laminaProfesional.add(marca);
		laminaIzquierda.add(laminaProfesional);

		// Insertamos tabla
		String[] nombresCabecera = { "Horario", "Dom 23", "Lun 24", "Mar 25", "Mie 26", "Jue 27", "Vie 28",
				"Sáb 29" };
		// Inicializamos modelo solo con los nombre de cabecera sin datos
		model = new DefaultTableModel(null, nombresCabecera);
		// Creamos el JTable
		JTable tablaAutos = new JTable(model) {
			public boolean isCellEditable(int row, int column) {
				return true;
			};
		};
		tablaAutos.setGridColor(Color.BLACK);
		tablaAutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Creamos un JscrollPane y le agregamos la JTable
		JScrollPane scrollPane = new JScrollPane(tablaAutos);
		laminaFormulario.add(laminaIzquierdaMadre);
		laminaFormulario.add(scrollPane);

		this.actualizarSemana();

		// Insertamos botones
		JPanel botonera = new JPanel();
		JButton botonAceptar = new JButton("Guardar");
		botonera.add(botonAceptar);
		JButton botonCancelar = new JButton("Cancelar");
		botonera.add(botonCancelar);
		laminaIzquierdaMadre.add(botonera, BorderLayout.SOUTH);

	}

	void actualizarSemana() {
		model.setRowCount(0);
		model.setRowCount(96);
		// model.setValueAt("00:00", 0, 0);
		Calendar date = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);

		for (int hora = 0; hora < 96; hora++) {
			model.setValueAt(df.format(date.getTime()), hora, 0);
			date.add(Calendar.MINUTE, 15);
		}

	}

	void updateMonth() {
		cal.set(Calendar.DAY_OF_MONTH, 1);

		String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
		int year = cal.get(Calendar.YEAR);
		labelMes.setText(month + " " + year);

		int startDay = cal.get(Calendar.DAY_OF_WEEK);
		int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int weeks = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);

		modelCalendario.setRowCount(0);
		modelCalendario.setRowCount(weeks + 1);

		int i = startDay - 1;
		for (int day = 1; day <= numberOfDays; day++) {
			modelCalendario.setValueAt(day, i / 7, i % 7);
			i = i + 1;
		}

	}
	
	 private JToolBar getToolBar() {
	        JToolBar barraBotones = new JToolBar();
	        Image imgPaciente = null;
	        Image imgDoctor = null;
			try {
				imgPaciente = ImageIO.read(getClass().getResource("/resources/patient.png"));
				imgDoctor = ImageIO.read(getClass().getResource("/resources/doctor.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        barraBotones.add(new JButton("Profesionales",new ImageIcon(imgDoctor)));
	        barraBotones.add(new JButton("Pacientes",new ImageIcon(imgPaciente)));
	        return barraBotones;
	    }
}
