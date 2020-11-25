package zjl.com.oa2.Appraisal.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Appraisal.Model.AppraisalPresenterImpl;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.R;

public class AppraisalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.tvCarType)
    TextView tvCarType;
    @Bind(R.id.etCarType)
    EditText etCarType;
    @Bind(R.id.tvCarModel)
    TextView tvCarModel;
    @Bind(R.id.etCarModel)
    EditText etCarModel;
    @Bind(R.id.tvCarYear)
    TextView tvCarYear;
    @Bind(R.id.etCarYear)
    EditText etCarYear;
    @Bind(R.id.btnAppraisalSearch)
    Button btnAppraisalSearch;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppraisalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppraisalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppraisalFragment newInstance(String param1, String param2) {
        AppraisalFragment fragment = new AppraisalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appraisal, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btnAppraisalSearch)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(),AppraisalActivity.class);
        String car_type = etCarType.getText().toString().trim();
        String car_model = etCarModel.getText().toString().trim();
        String car_year = etCarYear.getText().toString().trim();
        intent.putExtra("car_type",car_type);
        intent.putExtra("car_model",car_model);
        intent.putExtra("car_year",car_year);

        if (car_model.length() <= 0
                && car_type.length() <= 0
                && car_year.length() <= 0){
            Toast.makeText(getActivity(), "至少选择一项内容", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent);
    }
}
