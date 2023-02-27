import exec from 'k6/execution';
import http from 'k6/http';
import { check, sleep } from 'k6';
import { getRandomCard } from './mothers/card-morther.js'
import { getUserId } from './mothers/user-mother.js'

const endpoint = "192.168.100.75:8282";

export const options = {
    stages: [
        { duration: '3m', target: 7 },
        { duration: '5m', target: 10 },
        { duration: '7m', target: 0 },
    ],
};


export default function () {
    const userId = getUserId(exec.vu.idInTest)
    const res = http.post(`http://${endpoint}/users/${userId}/cards`, JSON.stringify(getRandomCard()), {
        headers: { 'Content-Type': 'application/json' },
    });
    check(res, { 'status was 200': (r) => r.status == 200 });
}