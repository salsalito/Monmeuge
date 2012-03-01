package fr.activity.meuge;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class DVPGallery extends Activity {
	//Adresse où se trouve l'ensemble des images gif (numérotées de 1 à 21).
	private final static String SERVER_IM = "http://mickael-lt.developpez.com//tutoriels/android/imagesfaq/";

	// GUI
	private Gallery gallery;
	private ImageView imgView;

	//Data
	private ArrayList<URL> mListImages;
	private Drawable mNoImage;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tableaux);

		//Récupération d'une image par défaut à afficher en cas d'erreur ou de liste vide
		mNoImage = this.getResources().getDrawable(R.drawable.ic_launcher);

		//Construction des URL pour les images
		mListImages = buildListImages();

		//Récupération du composant affichant l'image en grand de michel
		imgView = (ImageView)findViewById(R.id.imageview);
		
		//On lui met une image par défaut (la premiere de la liste ou à défaut l'image d'erreur)
		if (mListImages.size() <= 0) {
			imgView.setImageDrawable(mNoImage); 
		} else {
			setImage(imgView, mListImages.get(0));
		}
		//Récupération du composant affichant la liste des vignettes
		gallery = (Gallery) findViewById(R.id.gallery);
		//On lui donne notre adapter qui s'occupéra de l'alimenter en vignette
		gallery.setAdapter(new AddImgAdp(this));
		//Espacement entre les vignette
		gallery.setSpacing(10);

		//Lors d'un clic sur une des vignettes, on affiche l'image correspondante en grand
		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {			
				setImage(imgView, mListImages.get(position));
			}
		});

	}


	/**
	 * Permet de construire la liste des urls pour les images
	 * @return
	 */
	private ArrayList<URL> buildListImages() {
		int nbTotalImage = 21;
		ArrayList<URL> listFic = new ArrayList<URL>();
		for(int i = 1; i <= nbTotalImage; i++) {
			try {
				listFic.add(new URL(SERVER_IM + "/" + i + ".gif"));
			} catch (MalformedURLException e) {
				Log.e("DVP Gallery", "Erreur format URL : " + SERVER_IM + "/" + i + ".gif");
				e.printStackTrace();
			}
		}

		return listFic;
	}


	/**
	 * Notre adapter qui gère la liste des vignettes
	 * @author Mickaël Le Trocquer
	 */
	public class AddImgAdp extends BaseAdapter {
		int GalItemBg;
		private Context cont;

		public AddImgAdp(Context c) {
			cont = c;
			TypedArray typArray = obtainStyledAttributes(R.styleable.GalleryTheme);
			GalItemBg = typArray.getResourceId(R.styleable.GalleryTheme_android_galleryItemBackground, 0);
			typArray.recycle();
		}

		public int getCount() {
			return mListImages.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			ImageView imgView = null;
			//Récyclage du composant
			if (convertView == null) {
				imgView = new ImageView(cont);
			} else {
				imgView = (ImageView)convertView;
			}
			//On affecte notre image à la vignette
			if (mListImages.size() <= 0) {
				imgView.setImageDrawable(mNoImage); 
			} else {
				setImage(imgView, mListImages.get(position));
			}
			//On défini la taille de l'image
			imgView.setLayoutParams(new Gallery.LayoutParams(150, 150));
			imgView.setScaleType(ImageView.ScaleType.FIT_XY);
			//On fixe un arrière plan plus sympa
			imgView.setBackgroundResource(GalItemBg);
			imgView.setBackgroundResource(GalItemBg);

			return imgView;
		}
	}


	/**
	 * Méthode permettant de télécharger une image depuis une URL et de l'affecter à un composant de type ImageView
	 * @param aView
	 * @param aURL
	 */
	public void setImage(ImageView aView, URL aURL) {
		try {
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();

			// Bufferisation pour le téléchargement 
			BufferedInputStream bis = new BufferedInputStream(is, 8192);

			// Création de l'image depuis le flux des données entrant
			Bitmap bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();

			// Fixe l'image sur le composant ImageView
			aView.setImageBitmap(bm);
		
		} catch (IOException e) {
			aView.setImageDrawable(mNoImage);
			Log.e("DVP Gallery", "Erreur téléchargement image URL : " + aURL.toString());
			e.printStackTrace();
		} 
	}
}
