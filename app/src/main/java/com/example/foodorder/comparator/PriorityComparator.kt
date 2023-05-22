package com.example.foodorder.comparator

import com.example.foodorder.dto.Product

class PriorityComparator: Comparator<Product> {
    override fun compare(o1: Product?, o2: Product?): Int {
        if(o1 == null && o2 == null) {
            return 0
        } else if(o1 == null) {
            return 1
        } else if(o2 == null) {
            return -1
        }

        return if(o1.priority < o2.priority) {
            1;
        } else if(o1.priority > o2.priority) {
            -1
        } else {
            0
        }
    }
}