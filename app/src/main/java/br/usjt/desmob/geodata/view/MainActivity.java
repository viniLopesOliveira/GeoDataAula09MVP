package br.usjt.desmob.geodata.view;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import br.usjt.desmob.geodata.Contexto;
import br.usjt.desmob.geodata.R;
import br.usjt.desmob.geodata.model.entity.Pais;
import br.usjt.desmob.geodata.presenter.MainPresenter;


public class MainActivity extends Activity implements MainView{
    Spinner spinnerContinente;
    public static final String PAISES = "br.usjt.desmob.geodata.paises";
    Intent intent;
    ProgressBar timer;
    MainPresenter presenter = new MainPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerContinente = (Spinner) findViewById(R.id.spinnerContinente);
        spinnerContinente.setOnItemSelectedListener(new RegiaoSelecionada());
        timer = (ProgressBar) findViewById(R.id.timer);
        timer.setVisibility(View.INVISIBLE);
        Contexto.setValue(this);
        presenter.onCreate();
    }

    public void listarPaises(View view) {
        timer.setVisibility(View.VISIBLE);
        if(!presenter.isOnLine()){
            mensagemOffline();
        }
        new CarregaPaises().execute("pais");
    }

    @Override
    public String[] getRegioes() {
        return getApplicationContext().getResources().
                getStringArray(R.array.continentes);
    }


    public void mensagemOffline() {
        Toast.makeText(this,
                getApplicationContext().getResources().
                        getString(R.string.msg_rede),
                Toast.LENGTH_SHORT).show();
    }

    public void novaAtividade(Pais[] paises) {
        intent = new Intent(this, ListaPaisesActivity.class);
        intent.putExtra(PAISES, paises);
        startActivity(intent);
        timer.setVisibility(View.INVISIBLE);
    }

    private class RegiaoSelecionada implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String continente = (String) parent.getItemAtPosition(position);
            presenter.selecionarRegiao(continente);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class CarregaPaises extends AsyncTask<String, Void, Pais[]> {

        @Override
        protected Pais[] doInBackground(String... params) {
            return presenter.buscarPaises();
        }

        public void onPostExecute(Pais[] paises) {
            novaAtividade(paises);
        }
    }
}
