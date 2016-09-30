package five.team.tttdenysjonathan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.SharedPreferences;

public class Score extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        setupScore();
    }

    public void setupScore(){
        // Get labels and set score
        TextView lbl_scoreX_num = (TextView) findViewById(R.id.lbl_scoreX_num);
        TextView lbl_scoreO_num = (TextView) findViewById(R.id.lbl_scoreO_num);
        TextView lbl_scoreT_num = (TextView) findViewById(R.id.lbl_scoreT_num);

        lbl_scoreX_num.setText(String.valueOf(getIntent().getIntExtra("scoreX", 0)));
        lbl_scoreO_num.setText(String.valueOf(getIntent().getIntExtra("scoreO", 0)));
        lbl_scoreT_num.setText(String.valueOf(getIntent().getIntExtra("scoreT", 0)));
    }

    public void close(View view){
        setResult(RESULT_OK);
        finish();
    }
}
