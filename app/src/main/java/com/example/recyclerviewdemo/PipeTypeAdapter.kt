package com.example.recyclerviewdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PipeTypeAdapter(context: Context, val pipeTypeList: List<Any>) :
    ArrayAdapter<Any>(context, 0, pipeTypeList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view =
                LayoutInflater.from(parent!!.context).inflate(R.layout.item_spinner, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        if (pipeTypeList.get(0) is ExtraPipingDropdownListResponse) {
            pipeTypeList as List<ExtraPipingDropdownListResponse>
            vh.pipeView.setText(pipeTypeList.get(position).Name)
        }  else {
            vh.pipeView.setText("no data")
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return pipeTypeList[position]
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return this.getView(position, convertView, parent)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private class ItemHolder(row: View?) {
        val pipeView: TextView

        init {
            pipeView = row?.findViewById(R.id.txtType) as TextView
        }
    }
}