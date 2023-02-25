import exec from 'k6/execution';
import http from 'k6/http';
import { check, sleep } from 'k6';
import { getRandomCard } from './mothers/card-morther.js'
import { getUserId } from './mothers/user-mother.js'

const endpoint = "localhost:8080";

export const options = {
    stages: [
        { duration: '10s', target: 7 },
        { duration: '30s', target: 10 },
        { duration: '20s', target: 0 },
    ],
};


export default function () {
    const userId = getUserId(exec.vu.idInTest)
    const res = http.post(`http://${endpoint}/users/${userId}/cards`, JSON.stringify(getRandomCard()), {
        headers: { 'Content-Type': 'application/json' },
    });
    check(res, { 'status was 200': (r) => r.status == 200 });
    sleep(1);
}