package com.example.recyclerviewdemo

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_extra_pipe.view.*

class ChildAdapter(
    var context: Context,
    var extraPipingList: MutableList<ExtraPipingdetailsListVM>,
    var onGetExtraPipingData: OnGetExtraPipingData,
    var onClick: OnClick
) : RecyclerView.Adapter<ChildAdapter.Viewholder>() {
    var selectedMiscRateId: Long = 0L
    var selectedRate: Double = 0.0
    var selectedSpinnerValueStr = ""

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(itemModel: ExtraPipingdetailsListVM) {
            if (itemModel.ExtraPipingUnit.toString() == 0.0.toString()) {
                itemView.edtExtraPiping.setText("")
            } else {
                itemView.edtExtraPiping.setText(itemModel.ExtraPipingUnit.toString())
            }
            if (itemModel.Amount.toString() == 0.0.toString()) {
                itemView.edtExtraPipingAmount.setText("")
            } else {
                itemView.edtExtraPipingAmount.setText(itemModel.Amount.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_extra_pipe,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bindItem(extraPipingList[position])
        holder.itemView.edtExtraPiping.setTag(position)
        holder.itemView.setTag(position)
        if (holder.adapterPosition == 0) {
            holder.itemView.imgAddDetails.visibility = View.VISIBLE
            holder.itemView.imgDeleteDetails.visibility = View.GONE
        } else {
            holder.itemView.imgAddDetails.visibility = View.GONE
            holder.itemView.imgDeleteDetails.visibility = View.VISIBLE
        }

        var pipingList: ArrayList<ExtraPipingDropdownListResponse> =
            ArrayList<ExtraPipingDropdownListResponse>()
        pipingList.add(ExtraPipingDropdownListResponse(0, "Select Pipe Type", 0))
        pipingList.add(ExtraPipingDropdownListResponse(56, "12 MM Copper (265.00\\/Mtr)", 265))
        pipingList.add(ExtraPipingDropdownListResponse(54, "Cu(3\\/4\\\") (350.00\\/Mtr)", 350))
        pipingList.add(ExtraPipingDropdownListResponse(55, "GI(1\\/2)\\\" (330.00\\/Mtr)e", 330))

        val pipeSpinAdapter = PipeTypeAdapter(context, pipingList)
        holder.itemView.pipingTypeSpinner.setAdapter(pipeSpinAdapter)
        holder.itemView.pipingTypeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long
                ) {
                    selectedSpinnerValueStr = pipingList.get(p2).Name.toString()
                    selectedMiscRateId = pipingList.get(p2).MiscRateId!!.toLong()
                    selectedRate = pipingList.get(p2).Rate!!.toDouble()
                    if (selectedMiscRateId != 0L || selectedRate != 0.0) {
                        if (position == holder.itemView.edtExtraPiping.tag) {
                            Log.d(
                                "TAG",
                                "onItemSelected: " + position + " " + holder.adapterPosition + " " + holder.itemView.edtExtraPiping.tag
                            )
                            holder.itemView.edtExtraPiping.requestFocus()
                        }
                        onGetExtraPipingData.onGetExtraPipingData(selectedRate, selectedMiscRateId)
                        if (!holder.itemView.edtExtraPiping.text.toString()
                                .equals("") && holder.itemView.edtExtraPiping.text.toString()
                                .isNotEmpty()
                        ) {
                            var amount =
                                holder.itemView.edtExtraPiping.text.toString()
                                    .toDouble() * selectedRate
                            holder.itemView.edtExtraPipingAmount.setText(amount.toString())

                            var extraPipingdetailsListVM =
                                extraPipingList.get(holder.adapterPosition)
                            extraPipingdetailsListVM.ExtraPipingUnit =
                                holder.itemView.edtExtraPiping.text.toString()
                                    .toDouble()
                            extraPipingdetailsListVM.Amount = amount
                            extraPipingdetailsListVM.Rate = selectedRate
                            extraPipingdetailsListVM.MiscRateId = selectedMiscRateId
                            extraPipingList.set(holder.adapterPosition, extraPipingdetailsListVM)
                            amount
                            onGetExtraPipingData.onGetExtraPipingData(
                                selectedRate, selectedMiscRateId
                            )
                        } else {
                            var extraPipingdetailsListVM =
                                extraPipingList.get(holder.adapterPosition)
                            extraPipingdetailsListVM.Rate = selectedRate
                            extraPipingdetailsListVM.MiscRateId = selectedMiscRateId
                            extraPipingList.set(holder.adapterPosition, extraPipingdetailsListVM)
                            onGetExtraPipingData.onGetExtraPipingData(
                                selectedRate,
                                selectedMiscRateId
                            )
                        }
                    } else {
                        holder.itemView.edtExtraPiping.setText("")
                        holder.itemView.edtExtraPipingAmount.setText("")
                        var extraPipingdetailsListVM = extraPipingList.get(position)
                        extraPipingdetailsListVM.Rate = selectedRate
                        extraPipingdetailsListVM.MiscRateId = selectedMiscRateId
                        extraPipingList.set(position, extraPipingdetailsListVM)
                        onGetExtraPipingData.onGetExtraPipingData(selectedRate, selectedMiscRateId)
                    }
                }
            }
        for (i in 0 until pipingList.size) {
            if (pipingList.get(i).MiscRateId == extraPipingList.get(position).MiscRateId!!.toInt()
            ) {
                holder.itemView.pipingTypeSpinner.setSelection(i)
                break
            }
        }

        holder.itemView.llPiping.setOnClickListener {
            holder.itemView.pipingTypeSpinner.performClick()
        }

        holder.itemView.imgDeleteDetails.setOnClickListener {
            removeAt(holder.adapterPosition, it)
            onGetExtraPipingData.onGetExtraPipingData(
                selectedRate,
                selectedMiscRateId
            )
        }
        holder.itemView.imgAddDetails.setOnClickListener {
            extraPipingList.add(addBlankPipeType())
            notifyItemInserted(extraPipingList.size - 1)
           // notifyDataSetChanged()
        }
        holder.itemView.edtExtraPipingAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length > 0 && p0.isNotEmpty() && !p0.toString().equals("")) {
                    var extraPipingdetailsListVM = extraPipingList.get(holder.adapterPosition)
                    extraPipingdetailsListVM.Amount = p0.toString().toDouble()
                    extraPipingList.set(holder.adapterPosition, extraPipingdetailsListVM)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        holder.itemView.edtExtraPiping.addTextChangedListener(object : TextWatcher {
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
                    holder.itemView.edtExtraPipingAmount.setText("0.0")
                    var extraPipingdetailsListVM = extraPipingList.get(holder.adapterPosition)
                    extraPipingdetailsListVM.ExtraPipingUnit = 0.0
                    extraPipingList.set(holder.adapterPosition, extraPipingdetailsListVM)
                    onGetExtraPipingData.onGetExtraPipingData(selectedRate, selectedMiscRateId)
                } else {
                    var extraPipingdetailsListVM = extraPipingList.get(holder.adapterPosition)
                    extraPipingdetailsListVM.ExtraPipingUnit = charSequence.toString().toDouble()
                    extraPipingList.set(holder.adapterPosition, extraPipingdetailsListVM)
                }
            }

            override fun afterTextChanged(editable: Editable) {

                if (editable.length > 0) {
                    if (!holder.itemView.edtExtraPiping.text.toString().equals("")) {
                        var amount =
                            holder.itemView.edtExtraPiping.text.toString()
                                .toDouble() * selectedRate
                        holder.itemView.edtExtraPipingAmount.setText(amount.toString())
                        onGetExtraPipingData.onGetExtraPipingData(
                            selectedRate,
                            selectedMiscRateId
                        )
                    } else {
                        onGetExtraPipingData.onGetExtraPipingData(
                            selectedRate,
                            selectedMiscRateId
                        )
                    }
                    if (selectedRate != 0.0 ||
                        selectedMiscRateId != 0L
                    ) {
                        onGetExtraPipingData.onGetExtraPipingData(
                            selectedRate,
                            selectedMiscRateId
                        )
                    }
                }
            }
        })
        holder.itemView.txtPiping.setOnClickListener {
            onClick.onClick(position, holder.adapterPosition)
        }
    }

    fun removeAt(
        position: Int,
        view: View
    ) {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        extraPipingList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, extraPipingList.size)
        //notifyDataSetChanged()
    }

    fun updateList(filterdNames: MutableList<ExtraPipingdetailsListVM>) {
        extraPipingList.addAll(filterdNames)
        notifyDataSetChanged()
    }

    interface OnGetExtraPipingData {
        fun onGetExtraPipingData(
            rate: Double,
            miscRateId: Long
        )
    }

    override fun getItemCount(): Int {
        return extraPipingList.size
    }

    interface OnClick {
        fun onClick(position: Int, Holderposition: Int)
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
}