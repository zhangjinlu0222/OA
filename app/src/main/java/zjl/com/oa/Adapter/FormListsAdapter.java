package zjl.com.oa.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kyleduo.switchbutton.SwitchButton;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Base.FullyGridLayoutManager;
import zjl.com.oa.R;
import zjl.com.oa.RenewLoan.View.RenewLoanActivity;
import zjl.com.oa.Response.FormResponse;
import zjl.com.oa.Utils.LocalMediaUtil;

/**
 * Created by Administrator on 2017/5/26.
 */

public class FormListsAdapter extends BaseAdapter implements View.OnClickListener {
    private final int contro_style_count = 14;
    public List<FormResponse.Result.Form> formLists;
    private RenewLoanActivity context;
    private ListView listView;
    private LayoutInflater inflater;

    Map<Integer, String> mMapContent = new HashMap<>();
    ViewGroup.LayoutParams bakLayoutParams = null;

    /**图片上传
     * Form3*/
    public GridImageAdapter adapter;
    public FullyGridLayoutManager manager;

    /**时间选择
     * Form9*/
    private OnItemClickListener mOnItemClickListener;

    private int iCurrentIndex = -1;

    public interface OnItemClickListener {
        void onItemClick(View view , int itemPosition);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(FormListsAdapter.OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public FormListsAdapter(List<FormResponse.Result.Form> formLists, RenewLoanActivity context, ListView view) {
        this.formLists = formLists;
        this.context = context;
        this.listView = view;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return formLists.size();
    }

    @Override
    public int getViewTypeCount() {
        return contro_style_count;
    }

    @Override
    public int getItemViewType(int position) {
        return formLists.get(position).control_style;
    }

    @Override
    public Object getItem(int position) {
        return formLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Form1 form1 = null;
        Form2 form2 = null;
        Form3 form3 = null;
        Form6 form6 = null;
        Form7 form7 = null;
        Form8 form8 = null;
        Form9 form9 = null;
        Form10 form10 = null;
        Form11 form11 = null;
        Form12 form12 = null;
        Form13 form13 = null;

        int type = formLists.get(position).control_style;

        if (convertView == null) {
            switch (type) {
                case 1:
                    convertView = inflater.inflate(R.layout.form_item_1, null);
                    form1 = new Form1(convertView);
                    form1.img = convertView.findViewById(R.id.title_img);
                    form1.title = convertView.findViewById(R.id.title);
                    form1.content = convertView.findViewById(R.id.content);
                    form1.position = position;
                    convertView.setTag(form1);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.form_item_2, null);
                    form2 = new Form2(convertView);
                    form2.img = convertView.findViewById(R.id.title_img);
                    form2.title = convertView.findViewById(R.id.title);
                    form2.content = convertView.findViewById(R.id.content);
                    form2.position = position;
                    convertView.setTag(form2);
                    break;
                case 3:
                case 4:
                case 5:
                    convertView = inflater.inflate(R.layout.form_item_3, null);
                    form3 = new Form3(convertView);
                    form3.title = convertView.findViewById(R.id.title);
                    form3.recyclerView = convertView.findViewById(R.id.grid);
                    form3.switchButton = convertView.findViewById(R.id.uploadtype);
                    form3.uploadTypeHint = convertView.findViewById(R.id.uploadtypehint);
                    if (context.getInType().equals("bohui")){
                        form3.switchButton.setVisibility(View.VISIBLE);
                        form3.uploadTypeHint.setVisibility(View.VISIBLE);
                    }else{
                        form3.switchButton.setVisibility(View.GONE);
                        form3.uploadTypeHint.setVisibility(View.GONE);
                    }
                    form3.position = position;
                    convertView.setTag(form3);
                    break;
                case 6:
                    convertView = inflater.inflate(R.layout.form_item_6, null);
                    form6 = new Form6(convertView);
                    form6.title = convertView.findViewById(R.id.title);
                    form6.recyclerView = convertView.findViewById(R.id.grid);
                    form6.quota_iv_photo_selected = convertView.findViewById(R.id.quota_iv_photo_selected);

                    if (context.getInType().equals("bohui")){
                        form6.quota_iv_photo_selected.setVisibility(View.GONE);
                    }else{
                        form6.quota_iv_photo_selected.setVisibility(View.VISIBLE);
                    }
                    form6.position = position;
                    convertView.setTag(form6);
                    break;
                case 7:
                    convertView = inflater.inflate(R.layout.form_item_7, null);
                    form7 = new Form7(convertView);
                    form7.img = convertView.findViewById(R.id.title_img);
                    form7.head = convertView.findViewById(R.id.rl_head_bg);
                    form7.title = convertView.findViewById(R.id.title);
                    form7.position = position;
                    convertView.setTag(form7);
                    break;
                case 8:
                    convertView = inflater.inflate(R.layout.form_item_8, null);
                    form8 = new Form8(convertView);
                    form8.nsSelector = convertView.findViewById(R.id.ns_selector);
                    form8.title = convertView.findViewById(R.id.title);
                    form8.position = position;
                    convertView.setTag(form8);
                    break;
                case 9:
                    convertView = inflater.inflate(R.layout.form_item_9, null);
                    form9 = new Form9(convertView);
                    form9.title = convertView.findViewById(R.id.title);
                    form9.time = convertView.findViewById(R.id.time);
                    form9.position = position;
                    convertView.setTag(form9);
                    break;
                case 10:
                    convertView = inflater.inflate(R.layout.form_item_10, null);
                    form10 = new Form10(convertView);
                    form10.title = convertView.findViewById(R.id.title);
                    form10.content = convertView.findViewById(R.id.content);
                    form10.unit = convertView.findViewById(R.id.unit);
                    form10.position = position;
                    convertView.setTag(form10);
                    break;
                case 11:
                    convertView = inflater.inflate(R.layout.form_item_11, null);
                    form11 = new Form11(convertView);
                    form11.content = convertView.findViewById(R.id.content);
                    form11.position = position;
                    convertView.setTag(form11);
                    break;
                case 12:
                    convertView = inflater.inflate(R.layout.form_item_12, null);
                    form12 = new Form12(convertView);
                    form12.content = convertView.findViewById(R.id.content);
                    form12.tvRemarkWordsLeft = convertView.findViewById(R.id.remark_words_left);
                    form12.position = position;
                    convertView.setTag(form12);
                    break;
                case 13:
                    convertView = inflater.inflate(R.layout.form_item_13, null);
                    form13 = new Form13(convertView);
                    form13.ig_time = convertView.findViewById(R.id.time_icon);
                    form13.tv_time = convertView.findViewById(R.id.tv_time);
                    form13.tv_manager = convertView.findViewById(R.id.tv_manager);
                    form13.position = position;
                    convertView.setTag(form13);
                    break;
            }

        } else {
            switch (type) {
                case 1:
                    form1 = (Form1) convertView.getTag();
                    break;
                case 2:
                    form2 = (Form2) convertView.getTag();
                    break;
                case 3:
                case 4:
                case 5:
                    form3 = (Form3) convertView.getTag();
                    break;
                case 6:
                    form6 = (Form6) convertView.getTag();
                    break;
                case 7:
                    form7 = (Form7) convertView.getTag();
                    break;
                case 8:
                    form8 = (Form8) convertView.getTag();
                    break;
                case 9:
                    form9 = (Form9) convertView.getTag();
                    break;
                case 10:
                    form10 = (Form10) convertView.getTag();
                    break;
                case 11:
                    form11 = (Form11) convertView.getTag();
                    break;
                case 12:
                    form12 = (Form12) convertView.getTag();
                    break;
                case 13:
                    form13 = (Form13) convertView.getTag();
                    break;
            }
        }
        if (type == 1){
            setContentValue(form1,position);
        }else if (type == 2){
            setContentValue(form2,position);
        }else if (type == 3 || type == 4  ||type == 5 ){
            setContentValue(form3,position);
        }else if (type == 6){
            setContentValue(form6,position);
        }else if (type == 7){
            setContentValue(form7,position);
        }else if (type == 8){
            setContentValue(form8,position);
        }else if (type == 9){
            setContentValue(form9,position);
        }else if (type == 10){
            setContentValue(form10,position);
        }else if (type == 11){
            setContentValue(form11,position);
        }else if (type == 12){
            setContentValue(form12,position);
        }else if (type == 13){
            setContentValue(form13,position);
        }
        return convertView;
    }

    private void setContentValue(Form1 viewHolder,int pos){
        viewHolder.position = pos;

        viewHolder.title.setText(formLists.get(pos).getControl_title().trim());

        viewHolder.content.setHint(formLists.get(pos).getPlace_holder());

        String content = formLists.get(pos).getData_con();
        viewHolder.content.setText(content);

        boolean isReadOnly = formLists.get(pos).isRead_only();
        if (isReadOnly){
            viewHolder.content.setEnabled(false);
        }else{
            viewHolder.content.setEnabled(true);
        }

        int keyboardInputStyle = formLists.get(pos).getKeyboard_style();
        if (keyboardInputStyle == 3 && !isReadOnly){
            viewHolder.content.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }else if(keyboardInputStyle == 2 && !isReadOnly){
            viewHolder.content.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        }else{
            viewHolder.content.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        if (viewHolder.img != null){
            String url = formLists.get(pos).getTitle_img();
            if (url != null && url.length() > 0){
                Glide.with(context).load(formLists.get(pos).getTitle_img()).into(viewHolder.img);
                viewHolder.img.setVisibility(View.VISIBLE);
            }else{
                viewHolder.img.setVisibility(View.GONE);
            }
        }

        String unit = formLists.get(pos).getUnit();
        if (unit != null && unit.length() > 0){
            viewHolder.unit.setText(unit);
        }else{
            viewHolder.unit.setText("");
        }

        bakLayoutParams = viewHolder.content.getLayoutParams();

        if ((formLists.get(pos).getControl_title().contains("备注")
                || formLists.get(pos).getControl_title().contains("降额意见"))
                && viewHolder.content.getText().length() > 20){
            bakLayoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            viewHolder.content.setSingleLine(false);
            viewHolder.content.setLayoutParams(bakLayoutParams);
            viewHolder.content.invalidate();
        }else{
            viewHolder.content.setLayoutParams(bakLayoutParams);
            viewHolder.content.invalidate();
        }

        viewHolder.content.setOnTouchListener(new myOnTouchListener(pos));

//        viewHolder.content.clearFocus();

        if (pos == iCurrentIndex){
            viewHolder.content.requestFocus();
        }


    }


    private class myOnTouchListener implements View.OnTouchListener{

        int position;

        myOnTouchListener(int pos){
            this.position = pos;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP){
                iCurrentIndex = position;
            }
            return false;
        }
    }

    private void setContentValue(Form2 viewHolder,int pos){

        viewHolder.position = pos;

        if (viewHolder.img != null){
            String url = formLists.get(pos).getTitle_img();
            if (url != null && url.length() > 0){
                Glide.with(context).load(formLists.get(pos).getTitle_img()).into(viewHolder.img);
            }else{
                viewHolder.img.setVisibility(View.GONE);
            }
        }

        viewHolder.title.setText(formLists.get(pos).getControl_title().trim());

        viewHolder.content.setHint(formLists.get(pos).getPlace_holder().trim());
        String data = formLists.get(pos).getData_con();
        viewHolder.content.setText(data);

        boolean isReadOnly = formLists.get(pos).isRead_only();
        if (isReadOnly){
            viewHolder.content.setEnabled(false);
        }else{
            viewHolder.content.setEnabled(true);
        }
    }

    private void setContentValue(Form3 viewHolder,int pos){
        viewHolder.position = pos;
        viewHolder.title.setText(formLists.get(pos).getControl_title().trim());
        manager = new FullyGridLayoutManager(context, 4,
                GridLayoutManager.VERTICAL, false);

        viewHolder.recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(context,formLists.get(pos).getControl_title().trim(),
                new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {

                context.photoType = formLists.get(pos).getControl_title();

                context.showSheetDialog(formLists.get(pos).getControl_style());
            }
        });

        if (formLists.get(pos).getImgs().size() > 0){
            adapter.setList(LocalMediaUtil.getMediaList(formLists.get(pos).getImgs()));
        }

        adapter.setShowDel(true);

        viewHolder.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                context.onClickPic(position,pos);
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void setContentValue(Form6 viewHolder,int pos){
        viewHolder.position = pos;
        viewHolder.title.setText(formLists.get(pos).getControl_title().toString().trim());
        manager = new FullyGridLayoutManager(context, 4,
                GridLayoutManager.VERTICAL, false);

        viewHolder.quota_iv_photo_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formLists.get(pos).getControl_title().contains("评估报告")){

                    if (!context.reportFlag){
                        Glide.with(context).load(R.mipmap.checked).into(viewHolder.quota_iv_photo_selected);
                    }else{
                        Glide.with(context).load(R.mipmap.unchecked).into(viewHolder.quota_iv_photo_selected);
                    }
                    context.reportFlag = !context.reportFlag;

                }else if (formLists.get(pos).getControl_title().contains("车辆照片")){
                    if (!context.photoFlag){
                        Glide.with(context).load(R.mipmap.checked).into(viewHolder.quota_iv_photo_selected);
                    }else{
                        Glide.with(context).load(R.mipmap.unchecked).into(viewHolder.quota_iv_photo_selected);
                    }
                    context.photoFlag = !context.photoFlag;
                }
            }
        });

        viewHolder.recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(context, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {

                context.showSheetDialog(formLists.get(pos).getControl_style());
            }
        });
        adapter.setList(LocalMediaUtil.getMediaList(formLists.get(pos).getImgs()));
        adapter.setShowDel(false);
        adapter.setSelectMax(formLists.get(pos).getImgs().size());
        viewHolder.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                context.showQuotaImage(pos,position);
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void setContentValue(Form7 viewHolder,int pos){
        viewHolder.position = pos;
        viewHolder.title.setText(formLists.get(pos).getControl_title().trim());
        if (viewHolder.img != null && formLists.get(pos).getTitle_img().length() > 0){
            Glide.with(context).load(formLists.get(pos).getTitle_img()).into(viewHolder.img);
            viewHolder.img.setVisibility(View.VISIBLE);
        }else{
            viewHolder.img.setVisibility(View.GONE);
        }        String bg_color = formLists.get(pos).getBg_color().toString().trim();
        if (bg_color.length() > 0){
            viewHolder.head.setBackgroundColor(Color.parseColor(bg_color));
        }
    }
    private void setContentValue(Form8 viewHolder,int pos){
        viewHolder.position = pos;
        viewHolder.title.setText(formLists.get(pos).getControl_title().trim());
        List<String> units = Arrays.asList(formLists.get(pos).getUnit().split(","));
        viewHolder.datasource = units;
        viewHolder.nsSelector.attachDataSource(viewHolder.datasource);

        //初始值在units中不存在，1是没有值，2是给的下标
        String value = formLists.get(pos).getData_con();
        int index = units.indexOf(value);

        if (index < 0){
            //2给的是下标
            if (value != null && value.length() == 1){
                int value_index = Integer.parseInt(value);
                formLists.get(pos).setData_con(units.get(value_index - 1));
            }else{
                //1没有值
                formLists.get(pos).setData_con(units.get(0));
            }
        }else{
            //初始值在units中存在
            viewHolder.nsSelector.setSelectedIndex(index);
        }

        viewHolder.nsSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = formLists.get(pos).getUnit().split(",")[position] + "";
                formLists.get(pos).setData_con(value);
                context.disableColleaguePhone(value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                formLists.get(pos).setData_con(formLists.get(pos).getUnit().split(",")[0] +"");
            }
        });
    }

    private void setContentValue(Form9 viewHolder,int pos){
        viewHolder.position = pos;
        viewHolder.title.setText(formLists.get(pos).getControl_title().trim());
        String img = formLists.get(pos).getTitle_img().trim();
        if (viewHolder.img != null){
            if (img.length() > 0){
                viewHolder.img.setVisibility(View.VISIBLE);
                Glide.with(context).load(formLists.get(pos).getTitle_img()).into(viewHolder.img);
            }else{
                viewHolder.img.setVisibility(View.GONE);
            }
        }
        viewHolder.time.setHint(formLists.get(pos).getPlace_holder().trim());
        viewHolder.time.setText(formLists.get(pos).getData_con().trim());
        viewHolder.time.setTag(pos);
        viewHolder.time.setOnClickListener(this);
    }
    private void setContentValue(Form10 viewHolder,int pos){
        viewHolder.position = pos;
        viewHolder.title.setText(formLists.get(pos).getControl_title().trim());
        viewHolder.content.setText(formLists.get(pos).getData_con().trim());

        String unit = formLists.get(pos).getUnit();
        if (unit != null && unit.length() > 0){
            viewHolder.unit.setText(unit);
        }else{
            viewHolder.unit.setText("");
        }
    }
    private void setContentValue(Form11 viewHolder,int pos){
        viewHolder.position = pos;
        String data = formLists.get(pos).getData_con().trim();
        String hint = formLists.get(pos).getPlace_holder().trim();
        boolean readonly = formLists.get(pos).isRead_only();
        if (data.length() > 0){
            viewHolder.content.setText(data);
        }else{
            viewHolder.content.setHint(hint);
        }
        if (readonly){
            viewHolder.content.setEnabled(false);
        }else{
            viewHolder.content.setEnabled(true);
        }
    }

    private void setContentValue(Form12 viewHolder,int pos){
        viewHolder.position = pos;
        String data = formLists.get(pos).getData_con().trim();
        String hint = formLists.get(pos).getPlace_holder().trim();
        String title = formLists.get(pos).getControl_title().trim();
        boolean isReadOnly = formLists.get(pos).isRead_only();
        String submitField = formLists.get(pos).submit_field;
        viewHolder.title.setText(title);
        if (data.length() > 0){
            viewHolder.content.setText(data);
        }else{
            if (submitField != null && submitField.equals("")){
                viewHolder.content.setText("暂无");
            }else{
                viewHolder.content.setHint(hint);
            }
        }

        if (isReadOnly){
            viewHolder.content.setEnabled(false);
        }else{
            viewHolder.content.setEnabled(true);
        }
    }
    private void setContentValue(Form13 viewHolder,int pos){
        viewHolder.position = pos;

        if (viewHolder.ig_time != null){
            String url = formLists.get(pos).getTitle_img();
            if (url != null && url.length() > 0){
                Glide.with(context).load(formLists.get(pos).getTitle_img()).into(viewHolder.ig_time);
            }
        }
        viewHolder.tv_time.setText(formLists.get(pos).getControl_title().toString().trim());
        viewHolder.tv_manager.setText(formLists.get(pos).getData_con().toString().trim());
    }

    class Form1 {
        ImageView img;
        TextView title;
        EditText content;
        TextView unit;
        int position;

        @SuppressLint("ClickableViewAccessibility")
        public Form1(View convertView) {
            content = convertView.findViewById(R.id.content);
            content.setTag(this);
            unit = convertView.findViewById(R.id.unit);
            title = convertView.findViewById(R.id.title);
            img = convertView.findViewById(R.id.title_img);
            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                    formLists.get(position).setData_con(s.toString());

                    context.updateFormItemContent();
                }
            });

            content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    String s = content.getText().toString().trim();
                    if (hasFocus){
                        content.setSelection(s.length());
                    }else{
                        content.setText(s);
                    }
                }
            });
        }
    }

    class Form2 {
        ImageView img;
        TextView title;
        EditText content;

        int position;

        public Form2(View convertView) {
            content = convertView.findViewById(R.id.content);
            img = convertView.findViewById(R.id.title_img);
            title = convertView.findViewById(R.id.title);
            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    formLists.get(position).setData_con(s.toString());
                }
            });
        }
    }
    class Form3 {
        TextView title;
        TextView uploadTypeHint;
        RecyclerView recyclerView;
        SwitchButton switchButton;
        int position;

        public Form3(View convertView) {
            title = convertView.findViewById(R.id.title);
            uploadTypeHint = convertView.findViewById(R.id.uploadtypehint);
            recyclerView = convertView.findViewById(R.id.grid);
            switchButton = convertView.findViewById(R.id.uploadtype);
            switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    buttonView.setChecked(isChecked);
                    context.setUploadType(isChecked);
                }
            });
        }
    }
    class Form6 {
        TextView title;
        RecyclerView recyclerView;
        ImageView quota_iv_photo_selected;
        int position;

        public Form6(View convertView) {
            title = convertView.findViewById(R.id.title);
            quota_iv_photo_selected = convertView.findViewById(R.id.quota_iv_photo_selected);
            recyclerView = convertView.findViewById(R.id.grid);
        }
    }
    class Form7 {
        RelativeLayout head;
        ImageView img;
        TextView title;
        int position;

        public Form7(View convertView) {
            img = convertView.findViewById(R.id.title_img);
            title = convertView.findViewById(R.id.title);
            head = convertView.findViewById(R.id.rl_head_bg);
        }
    }
    class Form8 {
        TextView title;
        NiceSpinner nsSelector;
        int position;
        List datasource;

        public Form8(View convertView) {
            title = convertView.findViewById(R.id.title);
            nsSelector = convertView.findViewById(R.id.ns_selector);
            nsSelector.setArrowDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
        }
    }
    class Form9 {
        ImageView img;
        TextView title;
        TextView time;
        int position;

        public Form9(View convertView) {
            img = convertView.findViewById(R.id.title_img);
            title = convertView.findViewById(R.id.title);
            time = convertView.findViewById(R.id.time);
        }
    }
    class Form10 {
        TextView title;
        TextView content;
        TextView unit;
        int position;

        public Form10(View convertView) {
            title = convertView.findViewById(R.id.title);
            content = convertView.findViewById(R.id.content);
            unit = convertView.findViewById(R.id.unit);
        }
    }
    class Form11 {
        EditText content;
        int position;

        public Form11(View convertView) {
            content = convertView.findViewById(R.id.content);
            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    formLists.get(position).setData_con(s.toString());
                }
            });
        }
    }
    class Form12 {
        TextView title;
        EditText content;
        TextView tvRemarkWordsLeft;
        int position;

        public Form12(View convertView) {
            title = convertView.findViewById(R.id.title);
            content = convertView.findViewById(R.id.content);
            tvRemarkWordsLeft = convertView.findViewById(R.id.remark_words_left);
            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    formLists.get(position).setData_con(s.toString());
                    int words = s.toString().trim().length();
                    tvRemarkWordsLeft.setText(words + "/100字");
                }
            });
        }
    }
    class Form13 {
        ImageView ig_time;
        TextView tv_time;
        TextView tv_manager;
        int position;

        public Form13(View convertView) {
            ig_time = convertView.findViewById(R.id.time_icon);
            tv_time = convertView.findViewById(R.id.tv_time);
            tv_manager = convertView.findViewById(R.id.tv_manager);
        }
    }
}
