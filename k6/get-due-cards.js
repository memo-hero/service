import exec from 'k6/execution';
import http from 'k6/http';
import { check, sleep } from 'k6';
import { getUserId } from './mothers/user-mother.js'

const endpoint = "localhost:8080";

export const options = {
    stages: [
        { duration: '3m', target: 7 },
        { duration: '5m', target: 10 },
        { duration: '7m', target: 0 },
    ],
};


export default function () {
    const userId = getUserId(exec.vu.idInTest)
    const res = http.get(`http://${endpoint}/users/${userId}/cards/due`);
    check(res, { 'status was 200': (r) => r.status == 200 });
}