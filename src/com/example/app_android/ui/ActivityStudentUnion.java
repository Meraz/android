package com.example.app_android.ui;

import java.util.ArrayList;

import com.example.app_android.R;
import com.example.app_android.ui.elements.expandablelist.BaseExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.util.Logger;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class ActivityStudentUnion extends Activity {
	
	private static final String TAG = "ActivityCourseMaterial";
	private MyBaseExpandableListAdapter mExpandableListAdapter;
	private ArrayList<BaseExpandableListGroup> mExpandableListItems;
	private ExpandableListView mExpandableList;
	
	public interface ExpandableListCallBack{
		public void onChildClick();
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()a");
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_studentportal); 
        
		mExpandableList = (ExpandableListView) findViewById(R.id.ExpandableList);
		mExpandableListItems = SetStandardGroups();
		mExpandableListAdapter = new MyBaseExpandableListAdapter(this, mExpandableListItems);
		mExpandableListAdapter.setAdapter(mExpandableList);
    }        
    
    public ArrayList<BaseExpandableListGroup> SetStandardGroups() { // TODO engrish / swedrish
    	
    	// Return list of groups
    	ArrayList<BaseExpandableListGroup> finalList = new ArrayList<BaseExpandableListGroup>();
    	
    	// Temporary list of groups
    	BaseExpandableListGroup group;

    	// TODO REMOVE HARDCODE
    	String groupText = "Vad är studentkåren?";	    	
    	String childText = "Hej och välkommen till Karlskrona!Det är vi som är Blekinge Studentkår, vårt första möte kommer ske på vår Introduktionsvecka som vi hoppas att du kommer delta i. Men vilka är vi och vad är en studentkår? Jo, vi är som en stor familj av engagerade studenter som på ett eller annat sätt vill göra din studietid lite roligare och se till att din utbildning håller måttet! I grunden är en studentkår som en fackförening för studenterna vid en högskola.Varför ska man betala medlemskap? <br>Du kan se på detta på flera olika vinklar, beroende på vad du vill få ut av oss. Du kan bland annat tänka på det som en slags försäkring, går något med utbildningen snett, du känner dig felbehandlad av skolan eller liknande, då har du oss i ryggen som kommer stå upp för dig och se till att det löser sig på ett korrekt sätt! Ett annat sätt att se på medlemskapet är den studiesociala delen, du får som medlem chansen att delta i roligheter på din fritid så att dina år här kommer bli de bästa i ditt liv, som en liten bonus får du även komma in gratis på våra pubar. En vanlig missuppfattning är att pubarna drivs at pengar från medlemsavgiften och det är därför vi kan hålla så låga priser. Detta är helt fel och vi vill redan nu poängtera att absolut inga pengar från medlemskap går till våra pubar. Vi kan hålla de låga priserna på grund av att alla arbetar ideellt. För mer information om studentkårens olika sektioner och organ kika in på sidan för organ. www.bthstudent.se";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	groupText = "BOSS";	    	
    	childText = "BOSS (Blekinge Organiserade SpelStudenter) är en utbildningsförening för spelstudenter i Karlskrona. Vi representerar alla studenter på programmen Technical Artist, Spelprogrammering och Civilingenjör i Spel- och Programvaruteknik. Som student kan du se oss lite som din fackförening. Vi har förutom en styrelse utvalda representanter i varje klass som du kan vända dig till om du vill föra fram något viktigt eller har förslag på hur saker och ting borde förändras.";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	groupText = "BITS";	    	
    	childText = "Blekinge Ingenjörs Tekniska Studenter.";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	groupText = "Mårfin(t)";	    	
    	childText = "Mårfin(t) grundades 1999 i samband med att dåvarande Blekinge Internationella Hälsohögskola gick ihop med den statliga Högskolan Karlskrona/Ronneby som sedan döptes om till Blekinge Tekniska Högskola. Studentkåren vid BIH bildade då Mårfin(t) för att försäkra sig om att SSK-studenterna skulle ha någonstans att vända sig till. Efter ett par års aktivitet så minskade engagemanget och aktiviteten i Mårfin(t) dog tyvärr ut. Något som inte gynnade studenterna vid HAL (Sektionen för Hälsa). Tidigt 2011 så började ett intresse för att återuppliva Mårfin(t) dyka upp. Sagt och gjort så började arbetet med att få liv i föreningen. En ny styrelse sattes i maj 2011 och sedan dess pågår ett kontinuerligt arbete med att förbätta förutsättningarna för en bra studietid på BTH. Mårfin(t)s huvudsakliga mål är att bedriva utbildningsbevakning och kunna vara ett stöd för de studenter som upplever att de har problem med vissa delar av sin utbildning. Vi bedriver även till viss del studiesociala evenemang främst riktade mot studenter från HAL.";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	return finalList;
    }


    @Override
	protected void onDestroy() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onStop();
	}     
}