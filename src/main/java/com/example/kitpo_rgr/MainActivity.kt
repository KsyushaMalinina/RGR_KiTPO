package com.example.kitpo_rgr

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kitpo_rgr.Builder.Builder


class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var builder: Builder
        val mainText: TextView = findViewById(R.id.main_text)
        mainText.text = viewModel.logText
        val scroll: ScrollView = findViewById(R.id.scroller)
        val spinner: Spinner = findViewById(R.id.spinner)
        val spinnerList = Factory.getTypeNameList()
        val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, spinnerList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val Index: EditText = findViewById(R.id.index_inp)
        val valueInsert: EditText = findViewById(R.id.value_inp)
        val add: Button = findViewById(R.id.add)
        add.setOnClickListener {
            if (Index.text.isNullOrEmpty() || valueInsert.text.isNullOrEmpty()) return@setOnClickListener
            val index = Integer.parseInt(Index.text.toString())
            val value = Factory
                .getBuilderByName(spinner.selectedItem.toString())
                .parseObject(valueInsert.text.toString()) as Builder
            Index.setText("")
            valueInsert.setText("")

            viewModel.list.add(value,index)
            viewModel.logText = viewModel.logText + "\nЭлемент " + value.toString() + " вставлен в " + index
            mainText.text = viewModel.logText
        }

        spinner.setSelection(0,false)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                viewModel.list.init()
                valueInsert.hint = spinner.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val pushfront: Button = findViewById(R.id.pushfront)
        pushfront.setOnClickListener {
            if (valueInsert.text.isNullOrEmpty()) return@setOnClickListener
            val value = Factory
                .getBuilderByName(spinner.selectedItem.toString())
                .parseObject(valueInsert.text.toString()) as Builder
            valueInsert.setText("")

            viewModel.list.pushFront(value)
            viewModel.logText = viewModel.logText + "\nВставка в начало: " + value.toString()
            mainText.text = viewModel.logText
        }

        val pushend: Button = findViewById(R.id.pushend)
        pushend.setOnClickListener {
            if (valueInsert.text.isNullOrEmpty()) return@setOnClickListener
            val value = Factory
                .getBuilderByName(spinner.selectedItem.toString())
                .parseObject(valueInsert.text.toString()) as Builder
            valueInsert.setText("")

            viewModel.list.pushEnd(value)
            viewModel.logText = viewModel.logText + "\nВставка в конец: " + value.toString()
            mainText.text = viewModel.logText
        }

        val search: Button = findViewById(R.id.search)
        search.setOnClickListener {
            if (Index.text.isNullOrEmpty()) return@setOnClickListener
            val index = Integer.parseInt(Index.text.toString())
            Index.setText("")
            viewModel.logText = viewModel.logText + "\nИндекс " + index + ": " + viewModel.list.find(index)
            mainText.text = viewModel.logText
        }

        val delete: Button = findViewById(R.id.delete_indx)
        delete.setOnClickListener {
            if (Index.text.isNullOrEmpty()) return@setOnClickListener
            val index = Integer.parseInt(Index.text.toString())
            viewModel.list.delete(index)
            viewModel.logText = viewModel.logText + "\nИндекс: " + index + " удален"
            mainText.text =  viewModel.logText
            scroll.fullScroll(View.FOCUS_DOWN)
        }

        val sort: Button = findViewById(R.id.sort)
        sort.setOnClickListener {
            builder = Factory.getBuilderByName(spinner.selectedItem.toString())
            viewModel.list.sort(builder.getComparator())
            viewModel.logText = viewModel.logText + "\nСписок отсортирован!\n"
            mainText.text =  viewModel.logText
            scroll.fullScroll(View.FOCUS_DOWN)
        }

        val clear: Button = findViewById(R.id.clear)
        clear.setOnClickListener {
            viewModel.list.clear()
            viewModel.logText = viewModel.logText + "\nСписок очищен!\n"
            mainText.text =  viewModel.logText
            scroll.fullScroll(View.FOCUS_DOWN)
        }

        val show: Button = findViewById(R.id.show)
        show.setOnClickListener {
            viewModel.logText = viewModel.logText + "\n" + viewModel.list.toString()
            mainText.text =  viewModel.logText
            scroll.fullScroll(View.FOCUS_DOWN)
        }

        val save: Button = findViewById(R.id.save)
        save.setOnClickListener {
            Serialization.saveToFile(viewModel.list,"lstfile.txt", spinner.selectedItem.toString())
            viewModel.logText = viewModel.logText + "\nСписок сохранен!\n"
            mainText.text =  viewModel.logText
            scroll.fullScroll(View.FOCUS_DOWN)
        }

        val load: Button = findViewById(R.id.load)
        load.setOnClickListener {
            viewModel.list = Serialization.loadFile("lstfile.txt")
            viewModel.logText = viewModel.logText + "\nСписок загружен!\n"
            mainText.text =  viewModel.logText
            scroll.fullScroll(View.FOCUS_DOWN)
        }
    }
}