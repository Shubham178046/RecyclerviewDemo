package com.example.recyclerviewdemo

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_child_recyclerview.view.*
import kotlinx.android.synthetic.main.item_static_view.view.*


class ExtraPipingAdapter(
    var context: Context,
    var extraPipingList: MutableList<Model>,

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mRate = 0.0
    lateinit var childAdapter: ChildAdapter
    var mMiscRateId = 0L
    var SGSTper = 0.0
    var CGSTper = 0.0
    var extraPipeList = mutableListOf<ExtraPipingdetailsListVM>()
    var staticModel: StaticModel = StaticModel()

    class RecyclerviewViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class StaticViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemModel: StaticModel) {
            itemView.edtExtraPipingTotalAmounts.setText(
                itemModel.totalAmounts.toString()
            )
            itemView.edtExtraPipingCGSTs.setText(itemModel.cgst.toString())
            itemView.edtExtraPipingSGSTs.setText(itemModel.sgst.toString())
            itemView.edtExtraPipingPayableAmounts.setText(
                itemModel.payableAmounts.toString()
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            Model.RECYCLER_VIEW_TYPE -> {
                return RecyclerviewViewholder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_child_recyclerview,
                        parent,
                        false
                    )
                )
            }
            Model.STATIC_VIEW_TYPE -> {
                return StaticViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_static_view,
                        parent,
                        false
                    )
                )
            }
        }
        return null!!
    }

    override fun getItemViewType(position: Int): Int {
        return extraPipingList.get(position).type
        /*when (extraPipingList.get(position).type) {
            1 -> return Model.RECYCLER_VIEW_TYPE
            2 -> return Model.STATIC_VIEW_TYPE
            else -> return -1
        }*/
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var objects: Model = extraPipingList.get(position)
        if (objects != null) {
            when (objects.type) {
                Model.RECYCLER_VIEW_TYPE -> {
                    extraPipeList.clear()
                    extraPipeList.add(addBlankPipeType())
                    childAdapter = ChildAdapter(
                        context,
                        extraPipeList,
                        object : ChildAdapter.OnGetExtraPipingData {
                            override fun onGetExtraPipingData(rate: Double, miscRateId: Long) {
                                mRate = rate
                                mMiscRateId = miscRateId
                                setAmount()
                            }
                        }
                    ,object :ChildAdapter.OnClick{
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
                    (holder as RecyclerviewViewholder).itemView.child_recyclerview.layoutManager =
                        LinearLayoutManager(context)
                    (holder as RecyclerviewViewholder).itemView.child_recyclerview.setHasFixedSize(
                        false
                    )
                    (holder as RecyclerviewViewholder).itemView.child_recyclerview.adapter =
                        childAdapter
                    (holder as RecyclerviewViewholder).itemView.child_recyclerview.adapter?.notifyDataSetChanged()
                }
                Model.STATIC_VIEW_TYPE -> {
                    Log.d("TAG", "onBindViewHolder: "+staticModel.totalAmounts)
                    (holder as StaticViewHolder).bind(staticModel)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return extraPipingList.size
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

    fun setAmount() {
        var totalAmount = 0.0
        var editTextAmount = 0.0
        var cgstSgstRate = CGSTandSGSTPercentageResponse()
        cgstSgstRate.CGSTPer = 1.2
        cgstSgstRate.SGSTPer = 20.1
        CGSTper = cgstSgstRate.CGSTPer!!
        SGSTper = cgstSgstRate.SGSTPer!!
        if (extraPipeList.size > 0) {
            Log.d("Size", "setAmount: "+extraPipeList.size)
            for (i in 0 until extraPipeList.size) {
                if ((extraPipeList.get(i).Amount != null && extraPipeList.get(i).Amount!! > 0)
                ) {
                    editTextAmount += extraPipeList.get(i).Amount!!
                    /*totalAmount =
                        (if (holder.itemView.edtExtraPipingAmounts.text.toString() != null && holder.itemView.edtExtraPipingPayableAmounts.text.toString()
                                .isNotEmpty()
                        ) {
                            holder.itemView.edtExtraPipingAmount.text.toString()?.toDouble()
                        } else {
                            0.0
                        }) + editTextAmount*/
                    totalAmount = 0.0 + editTextAmount
                    var cgst: Double =
                        (totalAmount * CGSTper) / 100
                    var sgst: Double =
                        (totalAmount * SGSTper) / 100
                    var payableAmount = cgst + sgst + totalAmount
                    Log.d("TAG", "setAmount: " + totalAmount.toString())
                    staticModel.totalAmounts = totalAmount
                    staticModel.cgst = cgst
                    staticModel.sgst = sgst
                    staticModel.payableAmounts = payableAmount

                    val view = LayoutInflater.from(context).inflate(
                        R.layout.item_static_view,
                        null
                    )
                    view.edtExtraPipingTotalAmounts.setText(
                        totalAmount.toString()
                    )
                    view.edtExtraPipingCGSTs.setText(cgst.toString())
                    view.edtExtraPipingSGSTs.setText(sgst.toString())
                    view.edtExtraPipingPayableAmounts.setText(
                        payableAmount.toString()
                    )
                    Log.d("Data", "setAmount: " + view.edtExtraPipingTotalAmounts.text)
                }
            }
        } else {
            Log.d("Else", "setAmount: " + "Call")
            /* Log.d("Else", "setAmount: " + "Call")
             if (holder.itemView.edtExtraPipingTotalAmounts.text.toString() != null
             ) {
                 totalAmount =
                     (if (holder.itemView.edtExtraPipingAmounts.text.toString() != null && holder.itemView.edtExtraPipingAmounts.text.toString()
                             .isNotEmpty()
                     ) {
                         holder.itemView.edtExtraPipingAmounts.text.toString()?.toDouble()
                     } else {
                         0.0
                     })
                 var cgst: Double =
                     (totalAmount * CGSTper) / 100
                 var sgst: Double =
                     (totalAmount * SGSTper) / 100
                 var payableAmount = cgst + sgst + totalAmount

                 holder.itemView.edtExtraPipingTotalAmounts.setText(totalAmount.toString())
                 holder.itemView.edtExtraPipingCGSTs.setText(cgst.toString())
                 holder.itemView.edtExtraPipingSGSTs.setText(sgst.toString())
                 holder.itemView.edtExtraPipingPayableAmounts.setText(payableAmount.toString())

             } else {
                 holder.itemView.edtExtraPipingTotalAmounts.setText("")
                 holder.itemView.edtExtraPipingCGSTs.setText("")
                 holder.itemView.edtExtraPipingSGSTs.setText("")
                 holder.itemView.edtExtraPipingPayableAmounts.setText("")
             }*/
        }
    }
}
