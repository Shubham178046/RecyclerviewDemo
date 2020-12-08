package com.example.recyclerviewdemo

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdemo.adapter.ParentChildAdapter
import kotlinx.android.synthetic.main.activity_piping_details.*
import kotlinx.android.synthetic.main.item_extra_pipe.*
import java.security.AccessController.getContext


class PipingDetailsActivity : AppCompatActivity(), FocusListner {
    private var mLastClickTime: Long = 0
    lateinit var extraPipingAdapter: ParentChildAdapter
    var extraPipeList = mutableListOf<Model>()
    var pipingList: ArrayList<ExtraPipingDropdownListResponse> =
        ArrayList<ExtraPipingDropdownListResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_piping_details)
        actionListner()
        initViews()
    }

    private fun initViews() {
        extraPipeList.add(Model(Model.RECYCLER_VIEW_TYPE))
        extraPipingAdapter = ParentChildAdapter(this, extraPipeList)
        /*extraPipingAdapter = ExtraPipingAdapter(this@PipingDetailsActivity, extraPipeList,
            object : ExtraPipingAdapter.OnGetExtraPipingData {
                override fun onGetExtraPipingData(
                    rate: Double,
                    miscRateId: Long
                ) {
                    mRate = rate
                    mMiscRateId = miscRateId
                    setAmount()
                }
            }, object : ExtraPipingAdapter.OnClick {
                override fun onClick(position: Int, Holderposition: Int) {
                    for (i in 0 until extraPipeList.size) {
                        println(
                            "<><><><>" + extraPipeList.get(i).MiscRateId + " " + extraPipeList.get(
                                i
                            ).Amount + " " + extraPipeList.get(i).ExtraPipingUnit
                        )
                    }
                }
            }, this
        )*/
        /* val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
         layoutManager.isAutoMeasureEnabled()
         rvExtraPipe.setLayoutManager(object :
             LinearLayoutManager(this, VERTICAL, false) {
             override fun canScrollHorizontally(): Boolean {
                 return false
             }

             override fun canScrollVertically(): Boolean {
                 return false
             }
         })
        // rvExtraPipe.layoutManager = layoutManager
         rvExtraPipe.setNestedScrollingEnabled(false)
         rvExtraPipe.setHasFixedSize(false);
         rvExtraPipe.adapter = extraPipingAdapter
         rvExtraPipe.isNestedScrollingEnabled = false
         ViewCompat.setNestedScrollingEnabled(rvExtraPipe, false)*/
        // parentLayout?.descendantFocusability = FOCUS_BEFORE_DESCENDANTS
        //rvExtraPipe.adapter?.notifyDataSetChanged()
        // rvExtraPipe.smoothScrollToPosition(extraPipingAdapter.itemCount - 1)
        /* edtExtraPiping.setOnFocusChangeListener { view, b ->
             if (selectedSpinnerValueStr.equals("Select Pipe Type")) {
                 edtExtraPiping.setFocusableInTouchMode(false);
                 edtExtraPiping.setFocusable(false);
             } else {
                 edtExtraPiping.setFocusableInTouchMode(true);
                 edtExtraPiping.setFocusable(true);
             }
         }
         edtExtraPiping.addTextChangedListener(object : TextWatcher {
             override fun beforeTextChanged(
                 charSequence: CharSequence,
                 i: Int,
                 i1: Int,
                 i2: Int
             ) {

             }

             override fun onTextChanged(
                 charSequence: CharSequence,
                 i: Int,
                 i1: Int,
                 i2: Int
             ) {
                 if (charSequence.length == 0) {
                     edtExtraPipingAmount.setText("")
                     setAmount()
                 }
             }

             override fun afterTextChanged(editable: Editable) {
                 if (editable.length > 0) {
                     if (pipingTypeSpinner.selectedItemPosition == 0 || selectedSpinnerValueStr.equals(
                             "Select Pipe Type"
                         ) ||
                         selectedSpinnerValueStr.equals("")
                     ) {
                         *//* CommonUtils.showToast(
                            this@PipingDetailsActivity,
                            resources.getString(R.string.pipe_type_first),
                            MDToast.TYPE_INFO
                        )
                        // edtExtraPiping.setText("")*//*
                    } else {
                        if (!edtExtraPiping.text.toString().equals("")) {
                            var amount =
                                edtExtraPiping.text.toString().toDouble() * selectedRate
                            edtExtraPipingAmount.setText(amount.toString())
                            setAmount()
                        } else {
                            setAmount()
                        }
                    }
                } else {
                    if (!edtExtraPiping.text.toString().equals("")) {
                        var amount =
                            edtExtraPiping.text.toString().toDouble() * selectedRate
                        edtExtraPipingAmount.setText(amount.toString())
                        setAmount()
                    } else {
                        setAmount()
                    }
                }
            }
        })*/
        rvExtraPipe.layoutManager = LinearLayoutManager(this)
        rvExtraPipe.adapter = extraPipingAdapter
    }


    private fun actionListner() {
        /*llPiping.setOnClickListener {
            pipingTypeSpinner.performClick()
        }*/

        /*txtPiping.setOnClickListener {
            Log.d(
                "Size",
                "actionListner: " + "Model Class : " + extraPipeList.size + " " + "Recyclerview Size : " + rvExtraPipe.childCount
            )
        }*/

        /* imgAddDetails.setOnClickListener {
             val inputManager =
                 getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
             inputManager.hideSoftInputFromWindow(
                 it.windowToken,
                 InputMethodManager.HIDE_NOT_ALWAYS
             )
             extraPipeList.add(addBlankPipeType())
             extraPipingAdapter.notifyItemInserted(extraPipeList.size - 1)
             clearAllEditTextFocuses()
             *//*extraPipeList.add(addBlankPipeType())
            extraPipingAdapter = ExtraPipingAdapter(this@PipingDetailsActivity, extraPipeList,
                object : ExtraPipingAdapter.OnGetExtraPipingData {
                    override fun onGetExtraPipingData(
                        rate: Double,
                        miscRateId: Long
                    ) {
                        mRate = rate
                        mMiscRateId = miscRateId
                        setAmount()
                    }
                }, object : ExtraPipingAdapter.OnClick {
                    override fun onClick(position: Int, Holderposition: Int) {
                        for (i in 0 until extraPipeList.size) {
                            println(
                                "<><><><>" + extraPipeList.get(i).MiscRateId + " " + extraPipeList.get(
                                    i
                                ).Amount + " " + extraPipeList.get(i).ExtraPipingUnit
                            )
                        }
                    }
                })
            rvExtraPipe.adapter = extraPipingAdapter*//*
            //rvExtraPipe.adapter!!.notifyDataSetChanged()
            // rvExtraPipe.smoothScrollToPosition(extraPipingAdapter.itemCount - 1)
        }*/

    }

    private fun addBlankPipeType(): ExtraPipingdetailsListVM {
        Log.d("Add", "addBlankPipeType: " + "Call")
        var extraPipingdetailsListVM = ExtraPipingdetailsListVM()
        extraPipingdetailsListVM.Amount = 0.0
        extraPipingdetailsListVM.ExtraPipingUnit = 0.0
        extraPipingdetailsListVM.Rate = 0.0
        extraPipingdetailsListVM.MiscRateId = 0
        return extraPipingdetailsListVM
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun PipingTypeSpinner() {
        pipingList.add(ExtraPipingDropdownListResponse(0, "Select Pipe Type", 0))
        pipingList.add(ExtraPipingDropdownListResponse(56, "12 MM Copper (265.00\\/Mtr)", 265))
        pipingList.add(ExtraPipingDropdownListResponse(54, "Cu(3\\/4\\\") (350.00\\/Mtr)", 350))
        pipingList.add(ExtraPipingDropdownListResponse(55, "GI(1\\/2)\\\" (330.00\\/Mtr)e", 330))
        val pipeSpinAdapter = PipeTypeAdapter(applicationContext, pipingList)
//        pipingTypeSpinner.setAdapter(pipeSpinAdapter)
        /* pipingTypeSpinner?.onItemSelectedListener =
             object : AdapterView.OnItemSelectedListener {
                 override fun onNothingSelected(parent: AdapterView<*>?) {
                 }

                 override fun onItemSelected(
                     parent: AdapterView<*>?,
                     view: View?,
                     position: Int,
                     id: Long
                 ) {
                     if (position == 0) {
                         edtExtraPiping.setText("")
                         edtExtraPipingAmount.setText("")
                         setAmount()
                         return
                     }
                     selectedSpinnerValueStr = pipingList.get(position).Name!!
                     selectedMiscRateId = pipingList.get(position).MiscRateId!!.toLong()
                     selectedRate = pipingList.get(position).Rate!!.toDouble()
                     if (!edtExtraPiping.text.toString()
                             .equals("") && edtExtraPiping.text.toString() != null
                     ) {
                         val amount = edtExtraPiping.text.toString().toDouble() * pipingList.get(
                             pipingTypeSpinner.selectedItemPosition
                         ).Rate!!
                         edtExtraPipingAmount.setText(amount.toString())
                         setAmount()
                     } else {
                         setAmount()
                     }
                 }

             }*/

    }

    /*fun setAmount() {
        var totalAmount = 0.0
        var editTextAmount = 0.0
        var cgstSgstRate = CGSTandSGSTPercentageResponse()
        cgstSgstRate.CGSTPer = 1.2
        cgstSgstRate.SGSTPer = 20.1
        CGSTper = cgstSgstRate.CGSTPer!!
        SGSTper = cgstSgstRate.SGSTPer!!
        if (extraPipeList.size > 0) {
            for (i in 0 until extraPipeList.size) {
                if ((extraPipeList.get(i).Amount != null && extraPipeList.get(i).Amount!! > 0)
                ) {
                    editTextAmount += extraPipeList.get(i).Amount!!
                   *//* totalAmount =
                        (if (edtExtraPipingAmount.text.toString() != null && edtExtraPipingAmount.text.toString()
                                .isNotEmpty()
                        ) {
                            edtExtraPipingAmount.text.toString()?.toDouble()
                        } else {
                            0.0
                        }) + editTextAmount*//*
                    totalAmount = 0.0 + editTextAmount
                    var cgst: Double =
                        (totalAmount * CGSTper) / 100
                    var sgst: Double =
                        (totalAmount * SGSTper) / 100
                    var payableAmount = cgst + sgst + totalAmount
                    edtExtraPipingTotalAmount.setText(totalAmount.toString())
                    edtExtraPipingCGST.setText(cgst.toString())
                    edtExtraPipingSGST.setText(sgst.toString())
                    edtExtraPipingPayableAmount.setText(payableAmount.toString())
                }
            }
        } else {
            Log.d("Else", "setAmount: " + "Call")
            if (!edtExtraPipingAmount.text.toString()
                    .equals("") && edtExtraPipingTotalAmount.text.toString() != null
            ) {
                totalAmount =
                    (if (edtExtraPipingAmount.text.toString() != null && edtExtraPipingAmount.text.toString()
                            .isNotEmpty()
                    ) {
                        edtExtraPipingAmount.text.toString()?.toDouble()
                    } else {
                        0.0
                    })
                var cgst: Double =
                    (totalAmount * CGSTper) / 100
                var sgst: Double =
                    (totalAmount * SGSTper) / 100
                var payableAmount = cgst + sgst + totalAmount

             *//*   edtExtraPipingTotalAmount.setText(totalAmount.toString())
                edtExtraPipingCGST.setText(cgst.toString())
                edtExtraPipingSGST.setText(sgst.toString())
                edtExtraPipingPayableAmount.setText(payableAmount.toString())*//*

            } else {
                edtExtraPipingTotalAmount.setText("")
                edtExtraPipingCGST.setText("")
                edtExtraPipingSGST.setText("")
                edtExtraPipingPayableAmount.setText("")
            }
        }
    }*/

    private fun clearAllEditTextFocuses(): Boolean {
        Log.d("TAG", "clearAllEditTextFocuses: " + "Call")
        var v = currentFocus
        return if (v is EditText) {
            val list = FocusedEditTextItems()
            list.addAndClearFocus(v as EditText)
            var repeat = true
            do {
                v = currentFocus
                if (v is EditText) {
                    if (list.containsView(v)) repeat =
                        false else list.addAndClearFocus(v as EditText)
                } else repeat = false
            } while (repeat)
            val result = v !is EditText
            list.reset()
            result
        } else false
    }

    class FocusedEditTextItem constructor(val editText: EditText) {
        private val focusable: Boolean
        private val focusableInTouchMode: Boolean
        fun clearFocus() {
            if (focusable) editText.isFocusable = false
            if (focusableInTouchMode) editText.isFocusableInTouchMode = false
            editText.clearFocus()
        }

        fun reset() {
            if (focusable) editText.isFocusable = true
            if (focusableInTouchMode) editText.isFocusableInTouchMode = true
        }

        init {
            focusable = editText.isFocusable
            focusableInTouchMode = editText.isFocusableInTouchMode
        }
    }

    private class FocusedEditTextItems : ArrayList<FocusedEditTextItem?>() {
        fun addAndClearFocus(v: EditText) {
            val item = FocusedEditTextItem(v)
            add(item)
            item.clearFocus()
        }

        fun containsView(v: View): Boolean {
            var result = false
            for (item in this) {
                if (item!!.editText === v) {
                    result = true
                    break
                }
            }
            return result
        }

        fun reset() {
            for (item in this) item!!.reset()
        }
    }

    override fun removeFocus() {
        clearAllEditTextFocuses()
    }
}
