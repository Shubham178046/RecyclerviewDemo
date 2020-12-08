package com.example.recyclerviewdemo.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdemo.*
import kotlinx.android.synthetic.main.item_parent_reyclerview.view.*

class ParentChildAdapter(
    var context: Context,
    var extraPipingList: MutableList<Model>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mRate = 0.0
    lateinit var childAdapter: ChildAdapter
    var mMiscRateId = 0L
    var SGSTper = 0.0
    var CGSTper = 0.0
    var extraPipeList = mutableListOf<ExtraPipingdetailsListVM>()

    class RecyclerviewViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecyclerviewViewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_parent_reyclerview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        extraPipeList.clear()
        extraPipeList.add(addBlankPipeType())
        childAdapter = ChildAdapter(
            context,
            extraPipeList,
            object : ChildAdapter.OnGetExtraPipingData {
                override fun onGetExtraPipingData(rate: Double, miscRateId: Long) {
                    mRate = rate
                    mMiscRateId = miscRateId
                    setAmount(holder as RecyclerviewViewholder)
                }
            }, object : ChildAdapter.OnClick {
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
        (holder as RecyclerviewViewholder).itemView.demoRecyclerview.layoutManager =
            LinearLayoutManager(context)
        (holder as RecyclerviewViewholder).itemView.demoRecyclerview.adapter =
            childAdapter
        (holder as RecyclerviewViewholder).itemView.demoRecyclerview.adapter?.notifyDataSetChanged()
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

    fun setAmount(holder: RecyclerviewViewholder) {
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
                    totalAmount = 0.0 + editTextAmount
                    var cgst: Double =
                        (totalAmount * CGSTper) / 100
                    var sgst: Double =
                        (totalAmount * SGSTper) / 100
                    var payableAmount = cgst + sgst + totalAmount
                    holder.itemView.edtExtraPipingTotalAmounts.setText(
                        totalAmount.toString()
                    )
                    holder.itemView.edtExtraPipingCGSTs.setText(cgst.toString())
                    holder.itemView.edtExtraPipingSGSTs.setText(sgst.toString())
                    holder.itemView.edtExtraPipingPayableAmounts.setText(
                        payableAmount.toString()
                    )
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