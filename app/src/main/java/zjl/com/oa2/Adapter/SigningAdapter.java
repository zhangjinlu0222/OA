package zjl.com.oa2.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import zjl.com.oa2.CustomView.MyListView;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.GetSigningResponse;

/**
 * Created by Administrator on 2018/3/14.
 */

public class SigningAdapter extends BaseAdapter implements View.OnClickListener {
    private MyListView listView;
    private List<GetSigningResponse.Result.Data> dataList;
    private Context context;
    private LayoutInflater layoutInflater;
    private String ServiceFee,Pontage,ContractDate;

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public SigningAdapter(MyListView listView, List<GetSigningResponse.Result.Data> dataList, Context context) {
        this.listView = listView;
        this.dataList = dataList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    class ViewHolder {

        TextView name;
        EditText value;
        int position;

        public ViewHolder(View convertView) {
            name = convertView.findViewById(R.id.name);
            value = convertView.findViewById(R.id.value);
            value.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    dataList.get(position).setL_value(s.toString());
                }
            });
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.signing_item, null);
            viewHolder = new ViewHolder(convertView);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.value = convertView.findViewById(R.id.value);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.position = position;

        String name = dataList.get(position).getKey();
        viewHolder.name.setText(name);


        String value = dataList.get(position).getValue();

        if ("".equals(value)){
            viewHolder.value.setTextColor(Color.parseColor("#000000"));
            viewHolder.value.setBackgroundResource(R.mipmap.signing_et_bg);
            viewHolder.value.setEnabled(true);

        }

        if (!"".equals(value)){
            viewHolder.value.setText(value);
            viewHolder.value.setTextColor(Color.parseColor("#999999"));
            viewHolder.value.setBackground(null);
            viewHolder.value.setEnabled(false);
        }

        if (name.contains("服务费") || name.contains("过桥费")){
            viewHolder.value.setInputType(InputType.TYPE_CLASS_NUMBER);
            viewHolder.value.setBackgroundResource(R.mipmap.signing_et_bg);
        }

        if (name.contains("还款方式") && value.equals("")){
            viewHolder.value.setHint("");
            viewHolder.value.setBackground(null);
            viewHolder.value.setEnabled(false);
        }

        if (name.contains("合同")){
            viewHolder.value.setBackgroundResource(R.mipmap.signing_et_bg);
            viewHolder.value.setEnabled(true);
            viewHolder.value.setClickable(true);
            viewHolder.value.setFocusableInTouchMode(false);
            viewHolder.value.setHint("yyyy-mm-dd");
            viewHolder.value.setTag(position);
            viewHolder.value.setOnClickListener(this);
        }
        return convertView;
    }

    public String getPontage(){
        for (GetSigningResponse.Result.Data data:dataList){
            if (data.getKey().contains("过桥")){
                Pontage = data.getValue();
            }
        }
        return Pontage;
    }
    public String getServiceFee(){
        for (GetSigningResponse.Result.Data data:dataList){
            if (data.getKey().contains("服务")){
                ServiceFee = data.getValue();
            }
        }
        return ServiceFee;
    }
    public String getContractDate(){
        for (GetSigningResponse.Result.Data data:dataList){
            if (data.getKey().contains("合同")){
                ContractDate = data.getValue();
            }
        }
        return ContractDate;
    }
}
